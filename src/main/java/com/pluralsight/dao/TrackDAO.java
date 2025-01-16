package com.pluralsight.dao;

import com.pluralsight.domain.Track;
import com.pluralsight.enums.Genre;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TrackDAO implements DaoInterface<Track> {
    private final Map<Integer, Track> tracks = new HashMap<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Track save(Track track) {
        /*
            if null then IllegalArgumentException,
            if track's id is 0, it's a new track, and we add it to the tracks map
            if track exists and isn't null we update it after checking if the track's ID exists.
            if we try to update a nonexistent track print error msg
         */

        if (track == null) throw new IllegalArgumentException("Track cannot be null");
        if (track.getId() == 0) {
            int id = nextId.getAndIncrement();
            track.setId(id);
            System.out.println("Track ID" + track.getId() + "created.");
        }
        if (tracks.containsKey(track.getId())) {
                System.out.println("Track ID" + track.getId() + "updated.");
        }
        else {
            System.out.println("Track ID" + track.getId() + "doesn't exist.");
        }
        tracks.put(track.getId(), track);
        return track;
    }

    @Override
    public Optional<Track> getById(int id) {
        /*
         returns track with given ID if found in the tracks map
         if id doesn't exist returns null -> empty Optional
        */
        return Optional.ofNullable(tracks.get(id));
    }

    @Override
    public List<Track> getAll() {
        //retrieves collection of all values from the tracks map as ArrayList
        return new ArrayList<>(tracks.values());
    }

    @Override
    public void delete(int id) {
        //if track doesn't exist throw exception else remove.
        if (!tracks.containsKey(id)) {
            throw new IllegalArgumentException("Track ID" + id + " does not exist");
        }
        tracks.remove(id);
    }
}
