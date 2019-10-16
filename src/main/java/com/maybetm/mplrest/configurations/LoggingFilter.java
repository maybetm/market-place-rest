package com.maybetm.mplrest.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Фильтр для логгирования входящих и исходящих запросов
 * <p>
 * Подход стырен отсюда:
 * https://www.javadevjournal.com/spring/log-incoming-requests-spring/
 *
 * @author zebzeev-sv
 * @version 26.08.2019 18:35
 */
@Component
public class LoggingFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
																	FilterChain filterChain) throws ServletException, IOException {
		if (isAsyncDispatch(request)) {
			filterChain.doFilter(request, response);
		} else {
			doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
		}
	}

	private void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
															 FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} finally {
			loggingRequestAndResponse(request, response);
			response.copyBodyToResponse();
		}
	}

	private void loggingRequestAndResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) throws JsonProcessingException
  {

		final String prefixRq = "Request from: " + request.getRemoteAddr() + ":" + request.getLocalPort() + ";";
		final String prefixRs = "Response to: " + request.getRemoteAddr() + ":" + request.getLocalPort() + ";";

		if (logger.isInfoEnabled()) {
			loggingRequest(request, prefixRq);
			loggingResponse(response, prefixRs);
		}
	}

	private void loggingRequest(ContentCachingRequestWrapper request, String prefix) throws JsonProcessingException
  {
		logRequestHeader(request, prefix);
		logRequestBody(request, prefix);
		logRequestParams(request, prefix);
	}

	private void loggingResponse(ContentCachingResponseWrapper response, String prefix){
		logResponse(response, prefix);
	}

	private void logRequestHeader(ContentCachingRequestWrapper request, String prefix) {
		final String queryString = request.getQueryString();
		if (queryString == null) {
			logger.info("{} method: {}; path: {}", prefix, request.getMethod(), request.getRequestURI());
		} else {
			logger.info("{} method: {}; path: {}?{}", prefix, request.getMethod(), request.getRequestURI(), queryString);
		}
		logger.info("{} headers : {}", prefix, getHeaders.apply(request));
	}

  private void logRequestParams(ContentCachingRequestWrapper request, String prefix) throws JsonProcessingException
  {
    final Map<String, String[]> reqParamsMap = request.getParameterMap();
    if (reqParamsMap.size() > 0) {
      final String reqParams = new ObjectMapper().writeValueAsString(reqParamsMap);
      logger.info("{} request params: {};", prefix, reqParams);
    }
  }

  private void logRequestBody(ContentCachingRequestWrapper request, String prefix) {
		final byte[] content = request.getContentAsByteArray();
		if (content.length > 0) {
			loggingBody(content, request.getContentType(), StandardCharsets.UTF_8.displayName(), prefix);
		}
	}

	// преобразуем входящие хидеры в строку для логгирования
	private final Function<ContentCachingRequestWrapper, HttpHeaders> getHeaders = (request) ->
		Collections.list(request.getHeaderNames()).stream()
				.collect(Collectors.toMap(Function.identity(),
						(header) -> Collections.list(request.getHeaders(header)),
						(oldValue, newValue) -> newValue, HttpHeaders::new));

	private void logResponse(ContentCachingResponseWrapper response, String prefix) {
		final int status = response.getStatus();
		logger.info("{} http status: {}; http message: {};", prefix, status, HttpStatus.valueOf(status).getReasonPhrase());
		response.getHeaderNames().forEach(headerName ->
				response.getHeaders(headerName).forEach(headerValue ->
						logger.info("{} headers: [{}: {}]", prefix, headerName, headerValue)));
		final byte[] content = response.getContentAsByteArray();
		if (content.length > 0) {
			loggingBody(content, response.getContentType(), StandardCharsets.UTF_8.displayName(), prefix);
		}
	}

	private void loggingBody(byte[] content, String contentType, String contentEncoding, String prefix) {

		final MediaType mediaType = MediaType.valueOf(contentType);
		final boolean visible = MediaType.APPLICATION_JSON_UTF8.equals(mediaType);

		if (visible) {
			try {
				final String contentString = new String(content, contentEncoding);
				logger.info("{} body: {};", prefix, contentString);
			} catch (UnsupportedEncodingException e) {
				logger.error("{} body: [{} bytes content];", prefix, content.length);
			}
		} else {
			logger.info("{} body: [{} bytes content];", prefix, content.length);
		}
	}

	private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
		if (request instanceof ContentCachingRequestWrapper) {
			return (ContentCachingRequestWrapper) request;
		} else {
			return new ContentCachingRequestWrapper(request);
		}
	}

	private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
		if (response instanceof ContentCachingResponseWrapper) {
			return (ContentCachingResponseWrapper) response;
		} else {
			return new ContentCachingResponseWrapper(response);
		}
	}
}