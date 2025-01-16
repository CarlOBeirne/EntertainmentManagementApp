package com.pluralsight.service;

import com.pluralsight.dao.TrackDAO;
import com.pluralsight.domain.Track;

import java.util.List;
import java.util.Optional;

public class TrackDataService {
    private final TrackDAO trackDAO = new TrackDAO();

    public Track saveTrack(Track track) {
        if (track == null) throw new IllegalArgumentException("Track is null.");
        return trackDAO.save(track);
    }

    public Optional<Track> getById(int id) {
        return trackDAO.getById(id);
    }

    public List<Track> getAll() {
        return trackDAO.getAll();
    }

    public void delete(int id) {
        trackDAO.delete(id);
    }
}
