package com.pluralsight.service;

import com.pluralsight.dao.DaoInterface;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrackDataService {
    private final DaoInterface<Track> trackDAO;

    public TrackDataService(DaoInterface<Track> trackDAO) { this.trackDAO = trackDAO;}

    public Optional<Track> saveTrack(Track track) {
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

    public List<Track> getByTrackName(String title) {
        if (title == null) throw new IllegalArgumentException();
        return getAllTracks().stream()
                .filter(e -> e.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Track> getByTrackGenre(Genre genre) {
        if (genre == null) throw new IllegalArgumentException();
        return getAllTracks().stream().filter(e -> e.getGenre().name().equalsIgnoreCase(genre.name())).toList();
    }

    public List<Track> getByTrackYearReleased(int yearReleased) {
        if (yearReleased < 0) throw new IllegalArgumentException();
        return getAllTracks().stream().filter(e -> e.getYearReleased() == yearReleased).toList();
    }

    public List<Track> getByTrackArtist(Artist artist) {
        if (artist == null) throw new IllegalArgumentException();
        return getAllTracks().stream().filter(e -> e.getArtists().contains(artist)).toList();
    }
}
