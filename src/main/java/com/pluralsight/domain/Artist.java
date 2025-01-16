package com.pluralsight.domain;

import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;

import java.util.List;

public class Artist {
    private int id;
    private String name;
    private List<ArtistType> artistTypes;
    private List<Genre> genres;
    private String biography;
    private String nacionality;
    private String yearFounded;
    private List<Track> tracks;

    public Artist(List<Track> tracks, String yearFounded, String nacionality, String biography, List<Genre> genres, List<ArtistType> artistTypes, String name, int id) {
        this.tracks = tracks;
        this.yearFounded = yearFounded;
        this.nacionality = nacionality;
        this.biography = biography;
        this.genres = genres;
        this.artistTypes = artistTypes;
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArtistType> getArtistTypes() {
        return artistTypes;
    }

    public void setArtistTypes(List<ArtistType> artistTypes) {
        this.artistTypes = artistTypes;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getNacionality() {
        return nacionality;
    }

    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    public String getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(String yearFounded) {
        this.yearFounded = yearFounded;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
