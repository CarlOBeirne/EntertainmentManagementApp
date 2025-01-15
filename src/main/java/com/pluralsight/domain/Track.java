package com.pluralsight.domain;

import com.pluralsight.enums.Genre;

import java.util.List;

public class Track {
    private int id;
    private String title;
    private int durationSeconds;
    private Genre genre;
    // TODO: Replace ? with Artist when Artist class is created
    private List<?> artists;
    private int yearReleased;
    private int beatsPerMinute;

    public Track(String title, int durationSeconds, Genre genre, List<?> artists, int yearReleased, int beatsPerMinute) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.genre = genre;
        this.artists = artists;
        this.yearReleased = yearReleased;
        this.beatsPerMinute = beatsPerMinute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<?> getArtists() {
        return artists;
    }

    // TODO: Replace ? with Artist when Artist class is created
    public void setArtists(List<?> artists) {
        this.artists = artists;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public int getBeatsPerMinute() {
        return beatsPerMinute;
    }

    public void setBeatsPerMinute(int beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
    }
}
