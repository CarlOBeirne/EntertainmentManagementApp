package com.pluralsight.controller;

import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.Genre;
import com.pluralsight.service.TrackDataService;

import java.util.List;
import java.util.Optional;

public class TrackController {

    private final TrackDataService trackDataService = new TrackDataService();

    public Track saveTrack(Track track) {
        return trackDataService.saveTrack(track);
    }

    public Optional<Track> getTrackById (int id) {
        return trackDataService.getByTrackId(id);
    }

    public List<Track> getAllTracks () {
        return trackDataService.getAllTracks();
    }
    public void deleteTrack (int id) {
        trackDataService.deleteTrack(id);
    }

    public List<Track> getTrackByName (String name) {
        return trackDataService.getByTrackName(name);
    }

    public List<Track> getTrackByGenre(Genre genre) {
        return trackDataService.getByTrackGenre(genre);
    }

    public List<Track> getTrackByYearReleased(int yearReleased) {
        return trackDataService.getByTrackYearReleased(yearReleased);
    }

    public List<Track> getTrackByArtist (Artist artist){
        return trackDataService.getByTrackArtist(artist);
    }
}
