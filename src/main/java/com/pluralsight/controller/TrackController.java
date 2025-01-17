package com.pluralsight.controller;

import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.Genre;
import com.pluralsight.service.TrackDataService;

import java.util.List;
import java.util.Optional;

public class TrackController {

    private TrackDataService trackDataService = new TrackDataService();

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
        return trackDataService.getByName(name);
    }

    public List<Track> getTrackByGenre(Genre genre) {
        return trackDataService.getByGenre(genre);
    }

    public List<Track> getTrackByYearReleased(int yearReleased) {
        return trackDataService.getByYearReleased(yearReleased);
    }

    public List<Track> getTrackByArtist (Artist artist){
        return trackDataService.getByArtist(artist);
    }
}
