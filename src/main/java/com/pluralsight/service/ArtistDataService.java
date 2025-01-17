package com.pluralsight.service;

import com.pluralsight.dao.ArtistDAO;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Artist> getByName(String name) {
        return artistDAO.getAll().stream()
                .filter(artist -> artist.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Artist> getByArtistGenre(Genre genre) {
        return artistDAO.getAll().stream()
                .filter(artist -> artist.getGenres().contains(genre))
                .collect(Collectors.toList());
    }

    //TODO to be fixed - remove comment once Artist Domain is changed.
    public List<Artist> getByArtistNationality(String nationality) {
        return artistDAO.getAll().stream()
                .filter(artist -> artist.getNationality().toLowerCase().contains(nationality.toLowerCase()))
                .collect(Collectors.toList());
    }

    //TODO yearFounded as type int
    // but Artist Domain -> yearFounded = String
    public List<Artist> getByArtistYearFounded(String yearFounded) {
        return artistDAO.getAll().stream()
                .filter(artist -> artist.getYearFounded().equals(yearFounded))
                .collect(Collectors.toList());
    }

    public List<Artist> getByArtistType(ArtistType artistType) {
        return artistDAO.getAll().stream()
                .filter(artist -> artist.getArtistTypes().contains(artistType))
                .collect(Collectors.toList());
    }

    public List<Artist> getByArtistTrack(Track track) {
        return artistDAO.getAll().stream()
                .filter(artist -> artist.getTracks().contains(track))
                .collect(Collectors.toList());
    }

    public List<Artist> getByArtistBiography(String biography) {
        return artistDAO.getAll().stream()
                .filter(artist -> artist.getBiography().toLowerCase().contains(biography.toLowerCase()))
                .collect(Collectors.toList());
    }
}