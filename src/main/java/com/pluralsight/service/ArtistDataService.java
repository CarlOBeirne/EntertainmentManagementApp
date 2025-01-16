package com.pluralsight.service;

import com.pluralsight.dao.ArtistDAO;
import com.pluralsight.domain.Artist;

import java.util.List;
import java.util.Optional;

public class ArtistDataService {
    private final ArtistDAO artistDAO;

    //instantiate DAO
    public ArtistDataService() {
        artistDAO = new ArtistDAO();
    }

    //Save or Update artist if not null and if id isn't 0.
    public Artist saveArtist(Artist artist) {
        if (artist == null) {
            System.err.println("NullPointerException on SaveArtist.");
        }
        if (artist.getId() != 0) {
            System.err.println("Artist ID should be 0 for new artists");
        }
        return artistDAO.save(artist);
    }

    public Optional<Artist> getArtistById(int id) {
        return artistDAO.getById(id);
    }

    public List<Artist> getAllArtists(){
        return artistDAO.getAll();
    }

    public void deleteArtistById(int id) {
        artistDAO.delete(id);
    }
}