package com.pluralsight.dao;

import com.pluralsight.domain.Artist;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ArtistDAO implements DaoInterface<Artist> {
    private final HashMap<Integer, Artist> artists = new HashMap<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Artist save(Artist artist) {
        if(artist == null) throw new IllegalArgumentException("Artist cannot be null.");
        if (artist.getId() == 0) {
            int id = nextId.getAndIncrement();
            artist.setId(id);
            System.out.println("Artist ID " + artist.getId() + " created.");
        }
        if(artists.containsKey(artist.getId())) {
            System.out.println("Artist ID " + artist.getId() + " updated.");
        }
        else {
            System.err.println("Artist ID " + artist.getId() + " does not exist.");
        }
        artists.put(artist.getId(), artist);
        return artist;
    }

    @Override
    public Optional<Artist> getById(int id) {
        return Optional.ofNullable(artists.get(id));
    }

    @Override
    public List<Artist> getAll() {
        return artists.values().stream().toList();
    }

    @Override
    public void delete(int id) {
        if(!artists.containsKey(id)) throw new IllegalArgumentException("Artist ID " + id + " does not exist.");
        artists.remove(id);
    }
}
