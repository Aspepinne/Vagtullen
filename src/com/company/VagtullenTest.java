package com.company;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VagtullenTest {

    @DisplayName("Test if toll is not free during workdays")
    @Test
    void isTollFreeDateWorkday(){
        LocalDateTime date = LocalDateTime.parse("2020-06-30 11:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(Vagtullen.isTollFreeDate(date));
    }

    @DisplayName("Test if toll is free during weekends")
    @Test
    void isTollFreeDateWeekend(){
        LocalDateTime date = LocalDateTime.parse("2020-06-28 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(Vagtullen.isTollFreeDate(date));
    }

    @DisplayName("Test if toll is free in July")
    @Test
    void isTollFreeDateJuly(){
        LocalDateTime date = LocalDateTime.parse("2020-07-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(Vagtullen.isTollFreeDate(date));
    }

    @DisplayName("Test if each fee is right")
    @Test
    void getTollFeePerPassing(){

        LocalDateTime date = LocalDateTime.parse("2020-06-30 00:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(0, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-06-30 06:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(13, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-06-30 08:52", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(8, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-06-30 10:13", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(8, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-06-30 10:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(8, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-06-30 11:04", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(8, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-06-30 16:50", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(18, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-06-30 18:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(8, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-06-30 21:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(0, Vagtullen.getTollFeePerPassing(date));

        date = LocalDateTime.parse("2020-07-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(0, Vagtullen.getTollFeePerPassing(date));

//        06:00–06:29 8 kr
//        06:30–06:59 13 kr
//        07:00–07:59 18 kr
//        08:00–08:29 13 kr
//        08:30–14:59 8 kr
//        15:00–15:29 13 kr
//        15:30–16:59 18 kr
//        17:00–17:59 13 kr
//        18:00–18:29 8 kr
//        18:30–05:59 0 kr

    }

    @DisplayName("Test array length")
    @Test
    void TestDateLength() {
        Vagtullen vagtull = new Vagtullen("testData/Lab4.txt");
        assertEquals(vagtull.dateTest.length, vagtull.dateStringTest.length);
    }

    @DisplayName("Test if only the most expensive passage fee is paid within the hour")
    @Test
    void getMinuteDiff() {
        LocalDateTime[] dates = new LocalDateTime[6];
        dates[0] = LocalDateTime.parse("2020-06-29 07:28", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[1] = LocalDateTime.parse("2020-06-29 08:19", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[2] = LocalDateTime.parse("2020-06-29 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[3] = LocalDateTime.parse("2020-06-29 15:26", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[4] = LocalDateTime.parse("2020-06-29 16:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[5] = LocalDateTime.parse("2020-06-29 17:06", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(46, Vagtullen.getTotalFeeCost(dates));
    }

    @DisplayName("Test if the returned totalFee is right")
    @Test
    void getTotalFeeCost() {
        LocalDateTime[] dates = new LocalDateTime[10];
        dates[0] = LocalDateTime.parse("2020-06-30 00:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[1] = LocalDateTime.parse("2020-06-30 06:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[2] = LocalDateTime.parse("2020-06-30 08:52", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[3] = LocalDateTime.parse("2020-06-30 10:13", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[4] = LocalDateTime.parse("2020-06-30 10:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[5] = LocalDateTime.parse("2020-06-30 11:04", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[6] = LocalDateTime.parse("2020-06-30 16:50", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[7] = LocalDateTime.parse("2020-06-30 18:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[8] = LocalDateTime.parse("2020-06-30 21:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[9] = LocalDateTime.parse("2020-07-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(Math.min(Vagtullen.getTotalFeeCost(dates), 60), Vagtullen.getTotalFeeCost(dates));
    }

    @DisplayName("Test if date in file has invalid formatting")
    @Test
    void testDateTimeParseException(){
        String expectedOutput = "Invalid Format";
        assertEquals(expectedOutput, new Vagtullen("testData/TestFile1.txt").message);
    }

    @DisplayName("Test if file is empty")
    @Test
    void testNoSuchElementException(){
        String expectedOutput = "Empty Line";
        assertEquals(expectedOutput, new Vagtullen("testData/TestFile2.txt").message);
    }

}
