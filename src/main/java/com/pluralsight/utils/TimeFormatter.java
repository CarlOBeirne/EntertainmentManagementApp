package com.pluralsight.utils;

public class TimeFormatter {
    public static String fetchTrackDurationInMinutesAndSeconds(int durationSeconds) {
        if (durationSeconds < 0) throw new IllegalArgumentException("Duration cannot be a negative number");

        int minutes = Math.floorDiv(durationSeconds, 60);
        int seconds = durationSeconds - minutes * 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
