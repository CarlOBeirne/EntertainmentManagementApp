package com.pluralsight.controller;

import com.pluralsight.dao.ArtistDAO;
import com.pluralsight.dao.TrackDAO;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;
import com.pluralsight.service.ArtistTrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrackControllerTest {
    private TrackController trackController;

    @BeforeEach
    void setUp() {
        new TrackDAO().deleteAll();
        trackController = new TrackController(new ArtistTrackService());
    }
    @Test
    void saveTrack_shouldReturnNotFoundWhenNull() {
        assertEquals("Http 404 - Not Found", trackController.saveTrack(null));
    }

    @Test
    void saveTrack_shouldCreateNewTrack() {
        Track track = generateNewTracksForTests().get(0);
        assertEquals(0, track.getId());

        String createResponse = trackController.saveTrack(track).toString();
        String inDatabaseResponse = trackController.getTrackById(track.getId());

        assertEquals("Http 200 - OK", createResponse);
        assertEquals("Http 200 - OK", inDatabaseResponse);
    }

    @Test
    void deleteTrack_shouldReturnNotFoundWhenIdLessThanOrEqualZero() {
        Track track = generateNewTracksForTests().get(0);
        assertEquals("Http 404 - Not Found", trackController.deleteTrack(track.getId()));
    }

    @Test
    void deleteRequestDeleteArtist_shouldDeleteArtist() {
        Track track = generateNewTracksForTests().get(0);
        trackController.saveTrack(track);

        String response = trackController.deleteTrack(track.getId());

        assertEquals("Http 200 - OK", response);
    }

    @Test
    void getAllTracks_shouldReturnListOfAllTracks() {
        List<Track> tracksList = generateNewTracksForTests();
        for (Track track : tracksList) {
            trackController.saveTrack(track);
        }

        String response = trackController.getAllTracks();

        assertEquals("Http 200 - OK", response);
    }

    @Test
    void getTrackById_shouldReturnNotFoundWhenNoTracksInDatabase() {
        String response = trackController.getAllTracks();
        assertEquals("Http 404 - Not Found", response);
    }

    @Test
    void getRequestGetAllTrackdByGenre_shouldReturnNotFound() {
        assertEquals("Http 404 - Not Found", trackController.getTrackByGenre(null));
    }

    @Test
    void getRequestGetAllTracksByGenre_shouldReturnNotFoundIfListOfAllTracksByGenreIsEmpty() {
        Track track = generateNewTracksForTests().get(0);
        track.setGenre(Genre.ROCK);
        trackController.saveTrack(track);

        String response = trackController.getTrackByGenre(Genre.POP);

        assertEquals("Http 404 - Not Found", response);
    }



    private static List<Track> generateNewTracksForTests() {
        List<Artist> artists = List.of(
                new Artist(2000, "US", "test 1 bio", ArtistType.SOLO, "Test 1"),
                new Artist(2020, "GB", "test 2 bio", ArtistType.SOLO, "Test 2")
        );

        return List.of(
                new Track("Track 1", 240, Genre.FOLK, artists.stream().toList(), 2002, 340),
                new Track("Track 2", 220, Genre.ROCK, artists.stream().toList(), 2020, 420)
                );
    }

}
