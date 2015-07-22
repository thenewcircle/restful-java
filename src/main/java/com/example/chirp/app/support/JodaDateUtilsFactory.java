package com.example.chirp.app.support;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class JodaDateUtilsFactory {

	private final DateTimeZone defaultTimeZone;

	public JodaDateUtilsFactory(DateTimeZone defaultTimeZone) {
		this.defaultTimeZone = defaultTimeZone;
	}

	public LocalTime currentTime() {
		return new LocalTime(defaultTimeZone);
	}

	public LocalDateTime currentDateTime() {
		return new LocalDateTime(defaultTimeZone);
	}

	public LocalDate currentDate() {
		return new LocalDate(defaultTimeZone);
	}

	public LocalDateTime midnightToday() {
		return new LocalDate(defaultTimeZone).toLocalDateTime(LocalTime.MIDNIGHT);
	}

	public LocalDate toLocalDate(Object date) {
		if (date == null) {
			return null;

		} else if (date instanceof LocalDate) {
			return (LocalDate) date;

		} else if (date instanceof String) {
			return toLocalDate((String) date);

		} else if (date instanceof Long) {
			return toLocalDate((long) date);

		} else if (date instanceof java.util.Date) {
			return toLocalDate((java.util.Date) date);

		} else if (date instanceof Calendar) {
			return toLocalDate((Calendar) date);

		} else if (date instanceof LocalDateTime) {
			return toLocalDate((LocalDateTime) date);

		} else if (date instanceof DateTime) {
			return toLocalDate((DateTime) date);

		} else {
			String msg = String.format("Cannot convert objects of type %s to LocalDate.", date.getClass().getSimpleName());
			throw new IllegalArgumentException(msg);
		}
	}

	public LocalDate toLocalDate(String date) {
		if (date == null || date.isEmpty()) {
			return null;
		}

		date = date.replace('/', '-');

		if (date.contains(" ")) {
			date = date.substring(0, date.indexOf(' '));
			return new LocalDate(date, defaultTimeZone);

		} else if (date.contains("T")) {
			date = date.substring(0, date.indexOf('T'));
			return new LocalDate(date, defaultTimeZone);

		} else {
			return new LocalDate(date, defaultTimeZone);
		}
	}

	public LocalDate toLocalDate(long date) {
		if (date == 0) {
			return null;

		} else if (date > 0) {
			return new LocalDate(date, defaultTimeZone);

		} else {
			throw new IllegalArgumentException("The value \"date\" must be greater than or equal to zero.");
		}
	}

	public LocalDate toLocalDate(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new LocalDate(date, defaultTimeZone);
	}

	public LocalDate toLocalDate(Calendar date) {
		if (date == null) {
			return null;
		}
		return new LocalDate(date, defaultTimeZone);
	}

	public LocalDate toLocalDate(LocalDateTime date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}

	public LocalDate toLocalDate(DateTime date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}

	public LocalDate toLocalDate(int year, int monthOfYear, int dayOfMonth) {
		return new LocalDate(year, monthOfYear, dayOfMonth);
	}

	public LocalTime toLocalTime(Object date) {
		if (date == null) {
			return null;

		} else if (date instanceof LocalTime) {
			return (LocalTime) date;

		} else if (date instanceof String) {
			return toLocalTime((String) date);

		} else if (date instanceof Long) {
			return toLocalTime((long) date);

		} else if (date instanceof java.util.Date) {
			return toLocalTime((java.util.Date) date);

		} else if (date instanceof Calendar) {
			return toLocalTime((Calendar) date);

		} else if (date instanceof LocalDateTime) {
			return toLocalTime((LocalDateTime) date);

		} else if (date instanceof DateTime) {
			return toLocalTime((DateTime) date);

		} else {
			String msg = String.format("Cannot convert objects of type %s to LocalTime.", date.getClass().getSimpleName());
			throw new IllegalArgumentException(msg);
		}
	}

	public LocalTime toLocalTime(String date) {
		if (date == null) {
			return null;
		}

		date = date.replace('/', '-');

		if (date.contains("-") == false) {
			return new LocalTime(date, defaultTimeZone);

		} else if (date.contains(" ")) {
			date = date.replace(" ", "T");
			return new LocalDateTime(date, defaultTimeZone).toLocalTime();

		} else if (date.contains("T")) {
			return new LocalDateTime(date, defaultTimeZone).toLocalTime();

		} else {
			return LocalTime.MIDNIGHT;
		}
	}

	public LocalTime toLocalTime(long date) {
		if (date == 0) {
			return null;

		} else if (date > 0) {
			return new LocalTime(date, defaultTimeZone);

		} else {
			throw new IllegalArgumentException("The value \"date\" must be greater than or equal to zero.");
		}
	}

	public LocalTime toLocalTime(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new LocalTime(date, defaultTimeZone);
	}

	public LocalTime toLocalTime(Calendar date) {
		if (date == null) {
			return null;
		}
		return new LocalTime(date, defaultTimeZone);
	}

	public LocalTime toLocalTime(LocalDateTime date) {
		if (date == null) {
			return null;
		}
		return date.toLocalTime();
	}

	public LocalTime toLocalTime(DateTime date) {
		if (date == null) {
			return null;
		}
		return date.toLocalTime();
	}

	public LocalTime toLocalTime(int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
		return new LocalTime(hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
	}

	public LocalDateTime toLocalDateTime(Object date) {
		if (date == null) {
			return null;

		} else if (date instanceof LocalDateTime) {
			return (LocalDateTime) date;

		} else if (date instanceof String) {
			return toLocalDateTime((String) date);

		} else if (date instanceof Long) {
			return toLocalDateTime((long) date);

		} else if (date instanceof java.util.Date) {
			return toLocalDateTime((java.util.Date) date);

		} else if (date instanceof Calendar) {
			return toLocalDateTime((Calendar) date);

		} else if (date instanceof LocalDate) {
			return toLocalDateTime((LocalDate) date);

		} else if (date instanceof DateTime) {
			return toLocalDateTime((DateTime) date);

		} else {
			String msg = String.format("Cannot convert objects of type %s to LocalDateTime.", date.getClass().getSimpleName());
			throw new IllegalArgumentException(msg);
		}
	}

	public LocalDateTime toLocalDateTime(String date) {
		if (date == null) {
			return null;
		}

		date = date.replace('/', '-');

		if (date.contains(" ")) {
			date = date.replace(" ", "T");

		} else if (date.contains("T") == false) {
			date = date + "T" + LocalTime.MIDNIGHT;
		}

		return new LocalDateTime(date, defaultTimeZone);
	}

	public LocalDateTime toLocalDateTime(long date) {
		if (date == 0) {
			return null;

		} else if (date > 0) {
			return new LocalDateTime(date, defaultTimeZone);

		} else {
			throw new IllegalArgumentException("The value \"date\" must be greater than or equal to zero.");
		}
	}

	public LocalDateTime toLocalDateTime(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new LocalDateTime(date, defaultTimeZone);
	}

	public LocalDateTime toLocalDateTime(Calendar date) {
		if (date == null) {
			return null;
		}
		return new LocalDateTime(date, defaultTimeZone);
	}

	public LocalDateTime toLocalDateTime(LocalDate date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDateTime(LocalTime.MIDNIGHT);
	}

	public LocalDateTime toLocalDateTime(DateTime date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDateTime();
	}

	public LocalDateTime toLocalDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
		return new LocalDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
	}

	public DateTime toDateTime(Object date) {
		if (date == null) {
			return null;

		} else if (date instanceof DateTime) {
			return (DateTime) date;

		} else if (date instanceof String) {
			return toDateTime((String) date);

		} else if (date instanceof Long) {
			return toDateTime((long) date);

		} else if (date instanceof LocalDate) {
			return toDateTime((LocalDate) date);

		} else if (date instanceof LocalDateTime) {
			return toDateTime((LocalDateTime) date);

		} else if (date instanceof java.util.Date) {
			return toDateTime((java.util.Date) date);

		} else if (date instanceof Calendar) {
			return toDateTime((Calendar) date);

		} else {
			String msg = String.format("Cannot convert objects of type %s to DateTime.", date.getClass().getSimpleName());
			throw new IllegalArgumentException(msg);
		}
	}

	public DateTime toDateTime(String date) {
		if (date == null) {
			return null;
		}

		date = date.replace('/', '-');

		if (date.contains(" ")) {
			date = date.replace(" ", "T");
			return new DateTime(date, defaultTimeZone);

		} else if (date.contains("T")) {
			return new DateTime(date, defaultTimeZone);

		} else {
			return new LocalDate(date, defaultTimeZone).toDateTime(LocalTime.MIDNIGHT);

		}
	}

	public DateTime toDateTime(long date) {
		if (date == 0) {
			return null;

		} else if (date > 0) {
			return new DateTime(date, defaultTimeZone);

		} else {
			throw new IllegalArgumentException("The value \"date\" must be greater than or equal to zero.");
		}
	}

	public DateTime toDateTime(LocalDate date) {
		if (date == null) {
			return null;
		}
		return date.toDateTime(LocalTime.MIDNIGHT, defaultTimeZone);
	}

	public DateTime toDateTime(LocalDateTime date) {
		if (date == null) {
			return null;
		}
		return date.toDateTime(defaultTimeZone);
	}

	public DateTime toDateTime(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new DateTime(date.getTime(), defaultTimeZone);
	}

	public DateTime toDateTime(Calendar date) {
		if (date == null) {
			return null;
		}
		return new DateTime(date.getTime().getTime(), defaultTimeZone);
	}

	public DateTime toDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
		return new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
	}
}
