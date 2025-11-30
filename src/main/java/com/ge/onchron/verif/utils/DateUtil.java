package com.ge.onchron.verif.utils;

import com.github.sisyphsu.dateparser.DateParserUtils;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@UtilityClass
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private static final String CLOUDWATCH_LOG_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    private static final String CTE_LOG_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String RESPONSE_HEADER_DATE_FIELD_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final String CRITERIA_FIELD_FORMAT = "MMM d, yyyy";

    /**
     *  Parse from any date format to new format:
     *  e.g. Tue Jan 03 06:04:05 CST 2006
     *
     * @param date The date string to parse.
     * @return Parsed date as String.
     */
    public String displayTime(final String date) {
        return DateParserUtils.parseDate(date).toString();
    }
    /**
     * Returns LocalDateTime instance of response header date field e.g. Sun, 19
     * Mar 2023 15:10:52 GMT
     *
     * @param date the date string to parse.
     * @return LocalDateTime object.
     */
    public LocalDateTime getLocalDateTimeOfResponseDate(String date) {
        DateTimeFormatter df =
                DateTimeFormatter.ofPattern(RESPONSE_HEADER_DATE_FIELD_FORMAT);
        return LocalDateTime.parse(date, df);
    }
    private long convertDateToMilli(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()); // Or specify a time zone
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }

    public long convertLocalDateTimeOfResponseDateToMilli(String date) {
        return convertDateToMilli(date, RESPONSE_HEADER_DATE_FIELD_FORMAT);
    }

    public static long convertDateTimeStringToLong(String dateTimeString) {
        return convertDateToMilli(dateTimeString, CTE_LOG_FORMAT);
    }

    /**
     * Returns LocalDate instance of web format date field e.g. Mar 12, 2023
     *
     * @param date The date string to parse.
     * @return LocalDate object.
     */
    public LocalDate getCriteriaDateTime(String date) {
        DateTimeFormatter df =
                DateTimeFormatter.ofPattern(CRITERIA_FIELD_FORMAT);
        return LocalDate.parse(date, df);
    }

    /**
     * Returns LocalDate instance of web format date field e.g. Mar 12, 2023
     * @return LocalDate object formatted.
     */
    public LocalDate getCurrentDateTime() {
        String currentDateCriteriaFormatted = LocalDate.now().format(DateTimeFormatter.ofPattern(CRITERIA_FIELD_FORMAT));
        LOGGER.info(STR."Current Date: \{currentDateCriteriaFormatted}");
        return LocalDate.parse(currentDateCriteriaFormatted);
    }

    /**
     * Returns ZonedDateTime instance for a CloudWatch timestamp format e.g.
     * Mon Sep 02 09:40:11 IDT 2024
     *
     * @param date The date string to parse.
     * @return ZonedDateTime object.
     */
    public ZonedDateTime getZonedDateTimeFromCloudWatchLog(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CLOUDWATCH_LOG_FORMAT, Locale.ENGLISH)
                .withZone(ZoneId.systemDefault());
        return ZonedDateTime.parse(date, df);
    }

    /**
     * Compare timestamps ignoring time zones, truncating to minutes.
     *
     * @param expectedTimeStamp String representation of expected timestamp.
     * @param actualTimeStamp String representation of actual timestamp.
     * @return true if the timestamps match up to the minute, false otherwise.
     */
    public boolean compareTimestampsIgnoringTimeZone(final String expectedTimeStamp, final String actualTimeStamp) {
        try {
            LocalDateTime truncatedExpected = LocalDateTime.parse(expectedTimeStamp).truncatedTo(ChronoUnit.MINUTES);
            LocalDateTime truncatedActual = DateUtil.getZonedDateTimeFromCloudWatchLog(actualTimeStamp).truncatedTo(ChronoUnit.MINUTES).toLocalDateTime();

            LOGGER.info(STR."truncatedExpectedTimeStamp: \{truncatedExpected}");
            LOGGER.info(STR."truncatedActualTimeStamp: \{truncatedActual}");

            return truncatedExpected.equals(truncatedActual);
        } catch (DateTimeParseException e) {
            LOGGER.error(STR."Error parsing date: \{e.getMessage()}");
            return false;
        }
    }

    /**
     * @param dateStr String representation of expected timestamp.
     * @param datePattern String representation of Date Format
     *                    .
     * @return String format date
     */
    public static String parseDateToFormattedWithTimeOffSet(String dateStr, String datePattern, long timeOffset ) {
        SimpleDateFormat parser = new SimpleDateFormat(datePattern);
        parser.setTimeZone(TimeZone.getDefault());

        try {
            Date date = parser.parse(dateStr);
            long adjustedTime = date.getTime() - timeOffset;
            SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
            formatter.setTimeZone(TimeZone.getDefault());
            return formatter.format(new Date(adjustedTime));

        } catch (ParseException e) {
            throw new RuntimeException(STR."Unable to parse date string: \{e.getMessage()}");
        }
    }

}
