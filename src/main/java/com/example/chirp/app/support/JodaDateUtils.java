package com.example.chirp.app.support;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class JodaDateUtils {

	public static final DateTimeZone PDT = DateTimeZone.forID("America/Dawson");

	private static JodaDateUtilsFactory factory = new JodaDateUtilsFactory(null);

	private JodaDateUtils() {
	}

	public static JodaDateUtilsFactory newFactory(DateTimeZone timeZone) {
		return new JodaDateUtilsFactory(timeZone);
	}

	public static LocalTime currentTime() {
		return factory.currentTime();
	}

	public static LocalDateTime currentDateTime() {
		return factory.currentDateTime();
	}

	public static LocalDate currentDate() {
		return factory.currentDate();
	}

	public static LocalDateTime midnightToday() {
		return factory.midnightToday();
	}

	public static LocalDate toLocalDate(Object date) {
		return factory.toLocalDate(date);
	}

	public static LocalDate toLocalDate(String date) {
		return factory.toLocalDate(date);
	}

	public static LocalDate toLocalDate(long date) {
		return factory.toLocalDate(date);
	}

	public static LocalDate toLocalDate(java.util.Date date) {
		return factory.toLocalDate(date);
	}

	public static LocalDate toLocalDate(Calendar date) {
		return factory.toLocalDate(date);
	}

	public static LocalDate toLocalDate(LocalDateTime date) {
		return factory.toLocalDate(date);
	}

	public static LocalDate toLocalDate(DateTime date) {
		return factory.toLocalDate(date);
	}

	public static LocalDate toLocalDate(int year, int month, int day) {
		return factory.toLocalDate(year, month, day);
	}

	public static LocalTime toLocalTime(Object date) {
		return factory.toLocalTime(date);
	}

	public static LocalTime toLocalTime(String date) {
		return factory.toLocalTime(date);
	}

	public static LocalTime toLocalTime(long date) {
		return factory.toLocalTime(date);
	}

	public static LocalTime toLocalTime(java.util.Date date) {
		return factory.toLocalTime(date);
	}

	public static LocalTime toLocalTime(Calendar date) {
		return factory.toLocalTime(date);
	}

	public static LocalTime toLocalTime(LocalDateTime date) {
		return factory.toLocalTime(date);
	}

	public static LocalTime toLocalTime(DateTime date) {
		return factory.toLocalTime(date);
	}

	public static LocalTime toLocalTime(int hourOfDay, int minuteOfHour) {
		return factory.toLocalTime(hourOfDay, minuteOfHour, 0, 0);
	}

	public static LocalTime toLocalTime(int hourOfDay, int minuteOfHour, int secondOfMinute) {
		return factory.toLocalTime(hourOfDay, minuteOfHour, secondOfMinute, 0);
	}

	public static LocalTime toLocalTime(int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
		return factory.toLocalTime(hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
	}

	public static LocalDateTime toLocalDateTime(Object date) {
		return factory.toLocalDateTime(date);
	}

	public static LocalDateTime toLocalDateTime(String date) {
		return factory.toLocalDateTime(date);
	}

	public static LocalDateTime toLocalDateTime(long date) {
		return factory.toLocalDateTime(date);
	}

	public static LocalDateTime toLocalDateTime(java.util.Date date) {
		return factory.toLocalDateTime(date);
	}

	public static LocalDateTime toLocalDateTime(Calendar date) {
		return factory.toLocalDateTime(date);
	}

	public static LocalDateTime toLocalDateTime(LocalDate date) {
		return factory.toLocalDateTime(date);
	}

	public static LocalDateTime toLocalDateTime(DateTime date) {
		return factory.toLocalDateTime(date);
	}

	public static LocalDateTime toLocalDateTime(int year, int monthOfYear, int dayOfMonth) {
		return factory.toLocalDateTime(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
	}

	public static LocalDateTime toLocalDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour) {
		return factory.toLocalDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, 0, 0);
	}

	public static LocalDateTime toLocalDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute) {
		return factory.toLocalDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, 0);
	}

	public static LocalDateTime toLocalDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute,
			int millisOfSecond) {
		return factory.toLocalDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
	}

	public static DateTime toDateTime(Object date) {
		return factory.toDateTime(date);
	}

	public static DateTime toDateTime(String date) {
		return factory.toDateTime(date);
	}

	public static DateTime toDateTime(long date) {
		return factory.toDateTime(date);
	}

	public static DateTime toDateTime(LocalDate date) {
		return factory.toDateTime(date);
	}

	public static DateTime toDateTime(LocalDateTime date) {
		return factory.toDateTime(date);
	}

	public static DateTime toDateTime(java.util.Date date) {
		return factory.toDateTime(date);
	}

	public static DateTime toDateTime(Calendar date) {
		return factory.toDateTime(date);
	}

	public static DateTime toDateTime(int year, int monthOfYear, int dayOfMonth) {
		return factory.toDateTime(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
	}

	public static DateTime toDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour) {
		return factory.toDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, 0, 0);
	}

	public static DateTime toDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute) {
		return factory.toDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, 0);
	}

	public static DateTime toDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
		return factory.toDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
	}

	// static public boolean datesEqual(Date dateA, Date dateB) {
	// return EqualsUtils.datesEqual(dateA, dateB);
	// }
	//
	// static public boolean datesNotEqual(Date dateA, Date dateB) {
	// return EqualsUtils.datesNotEqual(dateA, dateB);
	// }
}
