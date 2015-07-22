package com.example.chirp.app.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class JodaDateUtilsFactoryTest {

	private static String timeZoneId = "America/Dawson";
	private static final DateTimeZone timeZone = DateTimeZone.forID(timeZoneId);

	private String stringTime = "13:15:32.136";
	private String stringDateMidnight = "2014-06-17";
	private String stringDateTimeT = "2014-06-17" + "T" + stringTime;
	private String stringDateTimeSp = "2014-06-17" + " " + stringTime;

	private long longDateTime = new DateTime(stringDateTimeT, timeZone).toDate().getTime();
	private long longDateMidnight = new DateTime(stringDateMidnight, timeZone).toDate().getTime();

	private Timestamp utilDateMidnight = new Timestamp(longDateMidnight);
	private Timestamp utilDateTime = new Timestamp(longDateTime);

	private Calendar calendar;
	private Calendar calendarMidnight;

	private JodaDateUtilsFactory factory;

	@Before
	public void beforeClass() throws Exception {
		factory = new JodaDateUtilsFactory(timeZone);

		calendar = new GregorianCalendar(TimeZone.getTimeZone(timeZoneId));
		calendar.set(Calendar.YEAR, 2014);
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.DAY_OF_MONTH, 17);
		calendar.set(Calendar.HOUR_OF_DAY, 13);
		calendar.set(Calendar.MINUTE, 15);
		calendar.set(Calendar.SECOND, 32);
		calendar.set(Calendar.MILLISECOND, 0);

		calendarMidnight = new GregorianCalendar(TimeZone.getTimeZone(timeZoneId));
		calendarMidnight.set(Calendar.YEAR, 2014);
		calendarMidnight.set(Calendar.MONTH, 5);
		calendarMidnight.set(Calendar.DAY_OF_MONTH, 17);
		calendarMidnight.set(Calendar.HOUR_OF_DAY, 0);
		calendarMidnight.set(Calendar.MINUTE, 0);
		calendarMidnight.set(Calendar.SECOND, 0);
		calendarMidnight.set(Calendar.MILLISECOND, 0);

		assertEquals(longDateTime, 1403036132136L);
		assertEquals(longDateMidnight, 1402988400000L);
	}

	@Test
	public void currentTime() {
		assertNotNull(JodaDateUtils.currentTime());
	}

	@Test
	public void currentDateTime() {
		assertNotNull(JodaDateUtils.currentDateTime());
	}

	@Test
	public void currentDate() {
		assertNotNull(JodaDateUtils.currentDate());
	}

	@Test
	public void midnightToday() {
		assertNotNull(JodaDateUtils.midnightToday());
	}

	@Test
	public void toLocalDate_Object() {
		assertNull(factory.toLocalDate((Object) null));

		validate(factory.toLocalDate((Object) new LocalDate(stringDateMidnight)));
		validate(factory.toLocalDate((Object) stringDateTimeT));
		validate(factory.toLocalDate((Object) longDateTime));
		validate(factory.toLocalDate((Object) new java.util.Date(longDateTime)));
		validate(factory.toLocalDate((Object) calendar));
		validate(factory.toLocalDate((Object) new LocalDateTime(stringDateTimeT)));
		validate(factory.toLocalDate((Object) new DateTime(stringDateTimeT)));

		try {
			factory.toLocalDate(new TestBean());
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Cannot convert objects of type TestBean to LocalDate.");
		}
	}

	@Test
	public void toLocalDate_String() {

		validate(factory.toLocalDate(stringDateMidnight));
		validate(factory.toLocalDate(stringDateMidnight.replace('-', '/')));

		validate(factory.toLocalDate(stringDateTimeT));
		validate(factory.toLocalDate(stringDateTimeT.replace('-', '/')));

		validate(factory.toLocalDate(stringDateTimeSp));
		validate(factory.toLocalDate(stringDateTimeSp.replace('-', '/')));

		assertNull(factory.toLocalDate((String) null));
	}

	@Test
	public void toLocalDate_Long() {

		validate(factory.toLocalDate(longDateTime));
		validate(factory.toLocalDate(longDateMidnight));

		assertNull(factory.toLocalDate(0));

		try {
			factory.toLocalDate(Integer.MIN_VALUE);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "The value \"date\" must be greater than or equal to zero.");
		}
	}

	@Test
	public void toLocalDate_UtilDate() {
		validate(factory.toLocalDate(utilDateTime));
		validate(factory.toLocalDate(utilDateMidnight));

		assertNull(factory.toLocalDate((java.util.Date) null));
	}

	@Test
	public void toLocalDate_GregorianCalendar() {
		validate(factory.toLocalDate(calendar));
		validate(factory.toLocalDate(calendarMidnight));

		assertNull(factory.toLocalDate((Calendar) null));
	}

	@Test
	public void toLocalDate_LocalDateTime() {

		validate(factory.toLocalDate(new LocalDateTime(stringDateTimeT)));

		assertNull(factory.toLocalDate((LocalDateTime) null));
	}

	@Test
	public void toLocalDate_DateTime() {

		validate(factory.toLocalDate(new DateTime(stringDateTimeT)));

		assertEquals(factory.toLocalDate((DateTime) null), null);
	}

	@Test
	public void toLocalDate_YMD() {
		validate(factory.toLocalDate(2014, 6, 17));
	}

	private void validate(LocalDate value) {
		assertNotNull(value);
		assertEquals(value.getYear(), 2014);
		assertEquals(value.getMonthOfYear(), 6);
		assertEquals(value.getDayOfMonth(), 17);
	}

	@Test
	public void toLocalTime_Object() {
		assertEquals(factory.toLocalTime((Object) null), null);

		validate(factory.toLocalTime((Object) new LocalTime(stringTime)), false, false);
		validate(factory.toLocalTime((Object) stringTime), false, false);
		validate(factory.toLocalTime((Object) longDateTime), false, false);
		validate(factory.toLocalTime((Object) new java.util.Date(longDateTime)), false, false);
		validate(factory.toLocalTime((Object) calendar), false, true);
		validate(factory.toLocalTime((Object) new LocalDateTime(stringDateTimeT)), false, false);
		validate(factory.toLocalTime((Object) new DateTime(stringDateTimeT)), false, false);

		try {
			factory.toLocalTime(new TestBean());
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Cannot convert objects of type TestBean to LocalTime.");
		}
	}

	@Test
	public void toLocalTime_String() {

		validate(factory.toLocalTime(stringTime), false, false);

		validate(factory.toLocalTime(stringDateMidnight), true, false);
		validate(factory.toLocalTime(stringDateMidnight.replace('-', '/')), true, false);

		validate(factory.toLocalTime(stringDateTimeT), false, false);
		validate(factory.toLocalTime(stringDateTimeT.replace('-', '/')), false, false);

		validate(factory.toLocalTime(stringDateTimeSp), false, false);
		validate(factory.toLocalTime(stringDateTimeSp.replace('-', '/')), false, false);

		assertNull(factory.toLocalTime((String) null));
	}

	@Test
	public void toLocalTime_Long() {

		validate(factory.toLocalTime(longDateTime), false, false);
		validate(factory.toLocalTime(longDateMidnight), true, false);

		assertEquals(factory.toLocalTime(0), null);

		try {
			factory.toLocalTime(Integer.MIN_VALUE);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "The value \"date\" must be greater than or equal to zero.");
		}
	}

	@Test
	public void toLocalTime_UtilDate() {
		validate(factory.toLocalTime(utilDateTime), false, false);
		validate(factory.toLocalTime(utilDateMidnight), true, false);

		assertNull(factory.toLocalTime((java.util.Date) null));
	}

	@Test
	public void toLocalTime_GregorianCalendar() {
		validate(factory.toLocalTime(calendar), false, true);
		validate(factory.toLocalTime(calendarMidnight), true, true);

		assertNull(factory.toLocalTime((Calendar) null));
	}

	@Test
	public void toLocalTime_LocalDateTime() {

		validate(factory.toLocalTime(new LocalDateTime(stringDateTimeT)), false, false);

		assertNull(factory.toLocalTime((LocalDateTime) null));
	}

	@Test
	public void toLocalTime_DateTime() {

		validate(factory.toLocalTime(new DateTime(stringDateTimeT)), false, false);

		assertNull(factory.toLocalTime((DateTime) null));
	}

	@Test
	public void toLocalTime_HMSM() {
		assertEquals(factory.toLocalTime(0, 0, 0, 0), LocalTime.MIDNIGHT);
		validate(factory.toLocalTime(13, 15, 32, 136), false, false);
	}

	private void validate(LocalTime value, boolean midnight, boolean calendar) {
		assertNotNull(value);
		assertEquals(value.getHourOfDay(), (midnight ? 0 : 13));
		assertEquals(value.getMinuteOfHour(), (midnight ? 0 : 15));
		assertEquals(value.getSecondOfMinute(), (midnight ? 0 : 32));
		assertEquals(value.getMillisOfSecond(), (midnight || calendar ? 0 : 136));
	}

	@Test
	public void toLocalDateTime_Object() {
		assertEquals(factory.toLocalDateTime((Object) null), null);

		validate(factory.toLocalDateTime((Object) new LocalDateTime(stringDateTimeT)), false, false);
		validate(factory.toLocalDateTime((Object) stringDateTimeT), false, false);
		validate(factory.toLocalDateTime((Object) longDateTime), false, false);
		validate(factory.toLocalDateTime((Object) new java.util.Date(longDateTime)), false, false);
		validate(factory.toLocalDateTime((Object) calendar), false, true);
		validate(factory.toLocalDateTime((Object) new LocalDate(stringDateMidnight)), true, false);
		validate(factory.toLocalDateTime((Object) new DateTime(stringDateTimeT)), false, false);

		try {
			factory.toLocalDateTime(new TestBean());
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Cannot convert objects of type TestBean to LocalDateTime.");
		}
	}

	@Test
	public void toLocalDateTime_String() {

		validate(factory.toLocalDateTime(stringDateMidnight), true, false);
		validate(factory.toLocalDateTime(stringDateMidnight.replace('-', '/')), true, false);

		validate(factory.toLocalDateTime(stringDateTimeT), false, false);
		validate(factory.toLocalDateTime(stringDateTimeT.replace('-', '/')), false, false);

		validate(factory.toLocalDateTime(stringDateTimeSp), false, false);
		validate(factory.toLocalDateTime(stringDateTimeSp.replace('-', '/')), false, false);

		assertNull(factory.toLocalDateTime((String) null));
	}

	@Test
	public void toLocalDateTime_Long() {

		validate(factory.toLocalDateTime(longDateTime), false, false);
		validate(factory.toLocalDateTime(longDateMidnight), true, false);

		assertEquals(factory.toLocalDateTime(0), null);

		try {
			factory.toLocalDateTime(Integer.MIN_VALUE);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "The value \"date\" must be greater than or equal to zero.");
		}
	}

	@Test
	public void toLocalDateTime_UtilDate() {
		validate(factory.toLocalDateTime(utilDateTime), false, false);
		validate(factory.toLocalDateTime(utilDateMidnight), true, false);

		assertNull(factory.toLocalDateTime((java.util.Date) null));
	}

	@Test
	public void toLocalDateTime_GregorianCalendar() {
		validate(factory.toLocalDateTime(calendar), false, true);
		validate(factory.toLocalDateTime(calendarMidnight), true, true);

		assertEquals(factory.toLocalDateTime((Calendar) null), null);
	}

	@Test
	public void toLocalDateTime_LocalDate() {

		validate(factory.toLocalDateTime(new LocalDate(stringDateMidnight)), true, false);

		assertNull(factory.toLocalDateTime((LocalDate) null));
	}

	@Test
	public void toLocalDateTime_DateTime() {

		validate(factory.toLocalDateTime(new DateTime(stringDateTimeT)), false, false);

		assertEquals(factory.toLocalDateTime((DateTime) null), null);
	}

	@Test
	public void toLocalDateTime_MDY_HMSM() {
		validate(factory.toLocalDateTime(2014, 6, 17, 13, 15, 32, 136), false, false);
	}

	private void validate(LocalDateTime value, boolean midnight, boolean calendar) {
		assertNotNull(value);
		assertEquals(value.getYear(), 2014);
		assertEquals(value.getMonthOfYear(), 6);
		assertEquals(value.getDayOfMonth(), 17);
		assertEquals(value.getHourOfDay(), (midnight ? 0 : 13));
		assertEquals(value.getMinuteOfHour(), (midnight ? 0 : 15));
		assertEquals(value.getSecondOfMinute(), (midnight ? 0 : 32));
		assertEquals(value.getMillisOfSecond(), (midnight || calendar ? 0 : 136));
	}

	@Test
	public void toDateTime_Object() {
		assertEquals(factory.toDateTime((Object) null), null);

		validate(factory.toDateTime((Object) new DateTime(stringDateTimeT)), false, false);
		validate(factory.toDateTime((Object) stringDateTimeT), false, false);
		validate(factory.toDateTime((Object) longDateTime), false, false);
		validate(factory.toDateTime((Object) new java.util.Date(longDateTime)), false, false);
		validate(factory.toDateTime((Object) calendar), false, true);
		validate(factory.toDateTime((Object) new LocalDate(stringDateMidnight)), true, false);
		validate(factory.toDateTime((Object) new LocalDateTime(stringDateTimeT)), false, false);

		try {
			factory.toDateTime(new TestBean());
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Cannot convert objects of type TestBean to DateTime.");
		}
	}

	@Test
	public void toDateTime_String() {

		validate(factory.toDateTime(stringDateMidnight), true, false);
		validate(factory.toDateTime(stringDateMidnight.replace('-', '/')), true, false);

		validate(factory.toDateTime(stringDateTimeT), false, false);
		validate(factory.toDateTime(stringDateTimeT.replace('-', '/')), false, false);

		validate(factory.toDateTime(stringDateTimeSp), false, false);
		validate(factory.toDateTime(stringDateTimeSp.replace('-', '/')), false, false);

		assertEquals(factory.toDateTime((String) null), null);
	}

	@Test
	public void toDateTime_Long() {

		validate(factory.toDateTime(longDateTime), false, false);
		validate(factory.toDateTime(longDateMidnight), true, false);

		assertEquals(factory.toDateTime(0), null);

		try {
			factory.toDateTime(Integer.MIN_VALUE);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "The value \"date\" must be greater than or equal to zero.");
		}
	}

	@Test
	public void toDateTime_LocalDate() {

		validate(factory.toDateTime(new LocalDate(stringDateMidnight)), true, false);

		assertEquals(factory.toDateTime((LocalDate) null), null);
	}

	@Test
	public void toDateTime_LocalDateTime() {

		validate(factory.toDateTime(new LocalDateTime(stringDateTimeT)), false, false);

		assertEquals(factory.toDateTime((LocalDateTime) null), null);
	}

	@Test
	public void toDateTime_GregorianCalendar() {
		validate(factory.toDateTime(calendar), false, true);
		validate(factory.toDateTime(calendarMidnight), true, true);

		assertEquals(factory.toDateTime((Calendar) null), null);
	}

	@Test
	public void toDateTime_UtilDate() {
		validate(factory.toDateTime(utilDateTime), false, false);
		validate(factory.toDateTime(utilDateMidnight), true, false);

		assertEquals(factory.toDateTime((java.util.Date) null), null);
	}

	@Test
	public void toDateTime_MDY_HMSM() {
		validate(factory.toDateTime(2014, 6, 17, 13, 15, 32, 136), false, false);
	}

	private void validate(DateTime value, boolean midnight, boolean calendar) {
		assertNotNull(value);
		assertEquals(value.getYear(), 2014);
		assertEquals(value.getMonthOfYear(), 6);
		assertEquals(value.getDayOfMonth(), 17);
		assertEquals(value.getHourOfDay(), (midnight ? 0 : 13));
		assertEquals(value.getMinuteOfHour(), (midnight ? 0 : 15));
		assertEquals(value.getSecondOfMinute(), (midnight ? 0 : 32));
		assertEquals(value.getMillisOfSecond(), (midnight || calendar ? 0 : 136));
	}
}