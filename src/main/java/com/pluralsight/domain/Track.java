package com.pluralsight.domain;

import com.pluralsight.enums.Genre;
import com.pluralsight.utils.ArtistHelper;

import java.util.List;

public class Track {
    private int id;
    private String title;
    private int durationSeconds;
    private Genre genre;
    private List<Artist> artists;
    private int yearReleased;
    private int beatsPerMinute;

    public Track(String title, int durationSeconds, Genre genre, List<Artist> artists, int yearReleased, int beatsPerMinute) {
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

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
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

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", durationSeconds=" + durationSeconds +
                ", genre=" + genre +
                ", artists=" + ArtistHelper.getArtistNameFromList(artists) +
                ", yearReleased=" + yearReleased +
                ", beatsPerMinute=" + beatsPerMinute +
                '}';
    }
}
