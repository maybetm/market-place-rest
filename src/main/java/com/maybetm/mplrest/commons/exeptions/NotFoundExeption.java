package com.maybetm.mplrest.commons.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: ZebzeevSV
 * @version: 05.05.2019 16:33
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundExeption extends RuntimeException {
}
