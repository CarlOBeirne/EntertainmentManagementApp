package com.pluralsight.service;

import com.pluralsight.dao.TrackDAO;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.Genre;

import java.util.List;
import java.util.Optional;

public class TrackDataService {
    private final TrackDAO trackDAO = new TrackDAO();

    public Track saveTrack(Track track) {
        if (track == null) throw new IllegalArgumentException("Track is null.");
        return trackDAO.save(track);
    }

    public Optional<Track> getByTrackId(int id) {
        return trackDAO.getById(id);
    }

    public List<Track> getAllTracks() {
        return trackDAO.getAll();
    }

    public void deleteTrack(int id) {
        trackDAO.delete(id);
    }

    public List<Track> getByName(String title) {
        return getAllTracks().stream().filter(e -> e.getTitle().equals(title)).toList();
    }

    public List<Track> getByGenre(Genre genre) {
        return getAllTracks().stream().filter(e -> e.getGenre().equals(genre)).toList();
    }

    public List<Track> getByYearReleased(int yearReleased) {
        return getAllTracks().stream().filter(e -> e.getYearReleased() == yearReleased).toList();
    }

    public List<Track> getByArtist(Artist artist) {
        return getAllTracks().stream().filter(e -> e.getArtists().contains(artist)).toList();
    }
}
