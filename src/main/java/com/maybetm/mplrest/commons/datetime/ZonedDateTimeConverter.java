package com.maybetm.mplrest.commons.datetime;

import javax.persistence.AttributeConverter;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author zebzeev-sv
 * @version 05.10.2019 23:19
 */
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Calendar> {

	@Override
	public Calendar convertToDatabaseColumn(ZonedDateTime attribute) {
		if (attribute == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(attribute.toInstant().toEpochMilli());
		calendar.setTimeZone(TimeZone.getTimeZone(attribute.getZone()));
		return calendar;
	}

	@Override
	public ZonedDateTime convertToEntityAttribute(Calendar dbData) {
		if (dbData == null) {
			return null;
		}

		return ZonedDateTime.ofInstant(dbData.toInstant(),
				dbData.getTimeZone().toZoneId());
	}


}
