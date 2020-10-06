package com.company;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Vagtullen {

    public String[] dateStringTest;
    public LocalDateTime[] dateTest;
    public String message;

    public Vagtullen(String inputFile) {
        try {
            readInputFile(inputFile);
        }catch (DateTimeParseException e){
            message = "Invalid Format";
            System.err.println(message);
        }catch (NoSuchElementException e){
            message = "Empty Line";
            System.err.println(message);
        }

    }

    public void readInputFile(String inputFile){

        try {
            Scanner sc = new Scanner(new File(inputFile));
            String[] dateStrings = sc.nextLine().split(", ");
            sc.close();
            LocalDateTime[] dates = new LocalDateTime[dateStrings.length];
            dateStringTest = dateStrings;
            dateTest = dates;
            for(int i = 0; i < dates.length; i++) {
                dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
            System.out.println("The total fee for the inputfile is " + getTotalFeeCost(dates));
        } catch(IOException e) {
            System.err.println("Could not read file " + inputFile);
        }
    }

    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;
        int passageFee = 0;
        int tempFee = 0;
        LocalDateTime intervalStart = dates[0];
        for(LocalDateTime date: dates) {
            System.out.println(date.toString());
            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
            if (diffInMinutes >= 60) {
                passageFee = getTollFeePerPassing(date);
                totalFee += passageFee;
                intervalStart = date;
            } else {
                tempFee = Math.max(passageFee, tempFee);
            }
        }
        return Math.min(totalFee + tempFee, 60);
    }

    public static int getTollFeePerPassing(LocalDateTime date) {
        if (isTollFreeDate(date)) return 0;
        int hour = date.getHour();
        int minute = date.getMinute();
        if (hour == 6  && minute <= 29) return 8;
        else if (hour == 6 ) return 13;
        else if (hour == 7 ) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour == 8 || hour >= 9 && hour < 15 && minute <= 30 ) return 8;
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour == 15 || hour == 16 ) return 18;
        else if (hour == 17 ) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
    }

    public static boolean isTollFreeDate(LocalDateTime date) {
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7;
    }

    public static void main(String[] args) {
        new Vagtullen("testData/Lab4.txt");
    }
}
