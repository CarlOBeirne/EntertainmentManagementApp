package com.pluralsight.domain;

import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;
import com.pluralsight.utils.TrackHelper;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private int id;
    private String name;
    private ArtistType artistType;
    private List<Genre> genres;
    private String biography;
    private String nationality;
    private int yearFounded;
    private List<Track> tracks;

    public Artist(int yearFounded, String nationality, String biography, ArtistType artistType, String name) {
        this.yearFounded = yearFounded;
        this.nationality = nationality;
        this.genres = new ArrayList<>();
        this.biography = biography;
        this.artistType = artistType;
        this.name = name;
        this.tracks = new ArrayList<>();
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

    public ArtistType getArtistType() {
        return artistType;
    }

    public void setArtistType(ArtistType artistType) {
        this.artistType = artistType;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(int yearFounded) {
        this.yearFounded = yearFounded;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
    }

    public void removeTrack(Track track) {
        tracks.remove(track);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artistType=" + artistType +
                ", genres=" + genres.toString() +
                ", biography='" + biography + '\'' +
                ", nationality='" + nationality + '\'' +
                ", yearFounded='" + yearFounded + '\'' +
                ", tracks=" + TrackHelper.getTrackTitleFromList(tracks) +
                '}';
    }
}
