package com.maybetm.mplrest.commons.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zebzeev-sv
 * @version 06.10.2019 2:58
 */
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

	public LocalDateTimeSerializer() {
		super(LocalDateTime.class);
	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
}
