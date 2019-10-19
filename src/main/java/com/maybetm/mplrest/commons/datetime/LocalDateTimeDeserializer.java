package com.maybetm.mplrest.commons.datetime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author zebzeev-sv
 * @version 06.10.2019 3:17
 */
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

	public LocalDateTimeDeserializer() {
		super(LocalDateTime.class);
	}

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return LocalDateTime.parse(p.readValueAs(String.class));
	}
}
