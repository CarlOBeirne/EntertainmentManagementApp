package com.pluralsight.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeFormatterTest {

    @Test
    void fetchTrackDurationInMinutesAndSeconds_shouldReturnTimeInMinutesAndSeconds() {
        // Arrange
        int secondsToTest1 = 301;
        String expectedResult1 = "05:01";

        int secondsToTest2 = 299;
        String expectedResult2 = "04:59";

        int secondsToTest3 = 300;
        String expectedResult3 = "05:00";

        int secondsToTest4 = 0;
        String expectedResult4 = "00:00";

        // Act
        String actualResult1 = TimeFormatter.fetchTrackDurationInMinutesAndSeconds(secondsToTest1);
        String actualResult2 = TimeFormatter.fetchTrackDurationInMinutesAndSeconds(secondsToTest2);
        String actualResult3 = TimeFormatter.fetchTrackDurationInMinutesAndSeconds(secondsToTest3);
        String actualResult4 = TimeFormatter.fetchTrackDurationInMinutesAndSeconds(secondsToTest4);

        // Assert
        assertEquals(expectedResult1, actualResult1);
        assertEquals(expectedResult2, actualResult2);
        assertEquals(expectedResult3, actualResult3);
        assertEquals(expectedResult4, actualResult4);
    }

    @Test
    void fetchTrackDurationInMinutes_shouldThrowExceptionWhenNegativeNumberPassed() {
        // Given
        int secondsToTest = -1;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> TimeFormatter.fetchTrackDurationInMinutesAndSeconds(secondsToTest));
    }

}