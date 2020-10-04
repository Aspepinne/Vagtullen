package com.company;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VagtullenTest {

    @Test
    void isTollFreeDate(){
        LocalDateTime date = LocalDateTime.parse("2020-06-30 00:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(Vagtullen.isTollFreeDate(date));

        date = LocalDateTime.parse("2020-07-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(Vagtullen.isTollFreeDate(date));
    }
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
    @Test
    void getTotalFeeCost(){


    }

}
