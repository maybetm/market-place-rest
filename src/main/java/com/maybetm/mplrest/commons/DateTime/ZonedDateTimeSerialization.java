package com.maybetm.mplrest.commons.datetime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The ISO date-time formatter that formats or parses a date-time with an
 * offset, such as '2011-12-03T10:15:30+01:00'.
 *
 * @author zebzeev-sv
 * @version 05.08.2019 10:53
 */
public class ZonedDateTimeSerialization extends JsonSerializer<ZonedDateTime>
{

  @Override
  public void serialize(ZonedDateTime date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
  {
    jsonGenerator.writeString(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(date));
  }
}
