package com.pluralsight.controller;

import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ArtistControllerTest {

    private final ArtistController artistController = new ArtistController();

    @Test
    void postRequestCreateArtist_shouldReturnNotFoundWhenNull() {
        assertEquals("Http 404 Not Found", artistController.postRequestCreateArtist(null));
    }

    @Test
    void postRequestCreateArtist_shouldReturnBadRequestWhenIdAlreadyExists() {
        Artist artist = generateNewArtistsForTests().getFirst();
        artistController.postRequestCreateArtist(artist);

        String secondCreateResponse = artistController.postRequestCreateArtist(artist);

        assertEquals("Http 400 Bad Request", secondCreateResponse);
    }

    @Test
    void postRequestCreateArtist_shouldCreateNewArtist() {
        Artist artist = generateNewArtistsForTests().getFirst();
        assertEquals(0, artist.getId());

        String createResponse = artistController.postRequestCreateArtist(artist);
        String inDatabaseResponse = artistController.getRequestGetArtistById(artist.getId());

        assertEquals("Http 200 OK", createResponse);
        assertEquals("Http 200 OK", inDatabaseResponse);
    }

    @Test
    void putRequestUpdateArtist_shouldUpdateArtist() {
        Artist artist = generateNewArtistsForTests().get(1);
        artistController.postRequestCreateArtist(artist);
        artist.setArtistType(ArtistType.GROUP);

        String updateResponse = artistController.putRequestUpdateArtist(artist.getId(), artist);

        assertEquals("Http 200 OK", updateResponse);
        assertTrue(Stream.of(artist.getArtistType())
                .allMatch(e -> e.equals(ArtistType.GROUP)));
    }

    @Test
    void putRequestUpdateArtist_shouldReturnNotFoundWhenNoArtistPassed() {
        assertEquals("Http 404 Not Found", artistController.putRequestUpdateArtist(1, null));
    }

    @Test
    void putRequestUpdateArtist_shouldReturnBadRequestIfIdLessThanOne() {
        Artist artist = generateNewArtistsForTests().getFirst();

        String response1 = artistController.putRequestUpdateArtist(1, artist);
        artist.setId(1);
        String response2 = artistController.putRequestUpdateArtist(0, artist);


        assertEquals("Http 400 Bad Request", response1);
        assertEquals("Http 400 Bad Request", response2);
    }

    @Test
    void putRequestUpdateArtist_shouldReturnConflictIfIdsDontMatch() {
        Artist artist = generateNewArtistsForTests().getFirst();
        artistController.postRequestCreateArtist(artist);

        String response = artistController.putRequestUpdateArtist(2, artist);

        assertEquals("Http 409 Conflict", response);
    }

    @Test
    void putRequestUpdateArtist_shouldReturnNotFoundWhenArtistIdNotInDatabase() {
        Artist artist = generateNewArtistsForTests().getFirst();
        artist.setId(Integer.MAX_VALUE);

        String response = artistController.putRequestUpdateArtist(Integer.MAX_VALUE, artist);

        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void deleteRequestDeleteArtist_shouldReturnNotFoundWhenIdLessThanOrEqualZero() {
        Artist artist = generateNewArtistsForTests().getFirst();
        assertEquals("Http 404 Not Found", artistController.deleteRequestDeleteArtist(artist.getId()));
    }

    @Test
    void deleteRequestDeleteArtist_shouldDeleteArtist() {
        Artist artist = generateNewArtistsForTests().getFirst();
        artistController.postRequestCreateArtist(artist);

        String response = artistController.deleteRequestDeleteArtist(artist.getId());

        assertEquals("Http 200 OK", response);
    }

    @Test
    void getRequestGetAllArtists_shouldReturnListOfAllArtists() {
        List<Artist> artistList = generateNewArtistsForTests();
        for (Artist artist : artistList) {
            artistController.postRequestCreateArtist(artist);
        }

        String response = artistController.getRequestGetAllArtists();

        assertEquals("Http 200 OK", response);
    }

    @Test
    void getRequestGetArtistById_shouldReturnNotFoundWhenNoArtistsInDatabase() {
        String response = artistController.getRequestGetAllArtists();
        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void getRequestGetAllArtistsByGenre_shouldReturnNotFound() {
        assertEquals("Http 404 Not Found", artistController.getRequestGetAllArtistsByGenre(null));
    }

    @Test
    void getRequestGetAllArtistsByGenre_shouldReturnNotFoundIfListOfAllArtistsByGenreIsEmpty() {
        Artist artist = generateNewArtistsForTests().getFirst();
        artist.setGenres(List.of(Genre.ROCK));
        artistController.postRequestCreateArtist(artist);

        String response = artistController.getRequestGetAllArtistsByGenre(Genre.POP);

        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void getRequestGetAllArtistsByGenre_shouldReturnListOfAllArtistsByGenre() {
        Artist artist = generateNewArtistsForTests().getFirst();
        artist.setGenres(List.of(Genre.ROCK));
        artistController.postRequestCreateArtist(artist);

        String response = artistController.getRequestGetAllArtistsByGenre(Genre.ROCK);

        assertEquals("Http 200 OK", response);
    }

    @Test
    void getRequestGetAllArtistsByArtistTrack_shouldReturnNotFound() {
        assertEquals("Http 404 Not Found", artistController.getRequestGetAllArtistsByArtistTrack(null));
    }

    @Test
    void getRequestGetAllArtistsByArtistTrack_shouldReturnNotFoundIfListOfAllArtistsByArtistTrackIsEmpty() {
        Artist artist = generateNewArtistsForTests().getFirst();
        Track track1 = new Track("Track Test", 200, Genre.POP, List.of(artist), 2025, 109);
        artist.setTracks(List.of(track1));
        artistController.postRequestCreateArtist(artist);
        Track track2 = new Track("Track Test", 200, Genre.POP, List.of(artist), 2025, 109);

        String response = artistController.getRequestGetAllArtistsByArtistTrack(track2);

        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void getRequestGetAllArtistsByArtistTrack_shouldReturnListOfAllArtistsByArtistTrack() {
        Artist artist = generateNewArtistsForTests().getFirst();
        Track track = new Track("Track Test", 200, Genre.POP, List.of(artist), 2025, 109);

        artist.setTracks(List.of(track));
        artistController.postRequestCreateArtist(artist);

        String response = artistController.getRequestGetAllArtistsByArtistTrack(track);

        assertEquals("Http 200 OK", response);
    }

    @Test
    void getRequestGetAllArtistsByArtistType_shouldReturnNotFound() {
        assertEquals("Http 404 Not Found", artistController.getRequestGetAllArtistsByArtistType(null));
    }

    @Test
    void getRequestGetAllArtistsByArtistType_shouldReturnNotFoundIfListOfAllArtistsByArtistTypeIsEmpty() {
        Artist soloArtist = generateNewArtistsForTests().getFirst();

        artistController.postRequestCreateArtist(soloArtist);

        String response = artistController.getRequestGetAllArtistsByArtistType(ArtistType.GROUP);

        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void getRequestGetAllArtistsByArtistType_shouldReturnListOfAllArtistsByArtistType() {
        Artist soloArtist = generateNewArtistsForTests().getFirst();
        artistController.postRequestCreateArtist(soloArtist);

        String response = artistController.getRequestGetAllArtistsByArtistType(ArtistType.SOLO);

        assertEquals("Http 200 OK", response);
    }

    @Test
    void getRequestGetAllArtistsByName_shouldReturnNotFound() {
        assertEquals("Http 404 Not Found", artistController.getRequestGetAllArtistsByName(null));
        assertEquals("Http 404 Not Found", artistController.getRequestGetAllArtistsByName("    "));
    }

    @Test
    void getRequestGetAllArtistsByName_shouldReturnNotFoundIfListOfAllArtistsByNameIsEmpty() {
        Artist artist = generateNewArtistsForTests().getFirst();

        artistController.postRequestCreateArtist(artist);

        String response = artistController.getRequestGetAllArtistsByName("No Name");

        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void getRequestGetAllArtistsByName_shouldReturnListOfAllArtistsByName() {
        Artist soloArtist = generateNewArtistsForTests().getFirst();
        artistController.postRequestCreateArtist(soloArtist);

        String response = artistController.getRequestGetAllArtistsByName("Test 1");

        assertEquals("Http 200 OK", response);
    }

    @Test
    void getRequestGetAllArtistsByNationality_shouldReturnNotFound() {
        assertEquals("Http 404 Not Found", artistController.getRequestGetAllArtistsByNationality(null));
        assertEquals("Http 404 Not Found", artistController.getRequestGetAllArtistsByNationality(""));
    }

    @Test
    void getRequestGetAllArtistsByNationality_shouldReturnNotFoundIfListOfAllArtistsByNationalityIsEmpty() {
        Artist artist = generateNewArtistsForTests().getFirst();

        artistController.postRequestCreateArtist(artist);

        String response = artistController.getRequestGetAllArtistsByNationality("XX");

        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void getRequestGetAllArtistsByNationality_shouldReturnListOfAllArtistsByNationality() {
        Artist soloArtist = generateNewArtistsForTests().getFirst();
        artistController.postRequestCreateArtist(soloArtist);

        String response = artistController.getRequestGetAllArtistsByNationality("Us");

        assertEquals("Http 200 OK", response);
    }

    @Test
    void getRequestGetAllArtistsByYearFounded_shouldReturnNotFound() {
        assertEquals("Http 404 Not Found", artistController.getRequestGetAllArtistsByYearFounded(-1));
    }

    @Test
    void getRequestGetAllArtistsByYearFounded_shouldReturnNotFoundIfListOfAllArtistsByYearFoundedIsEmpty() {
        Artist artist = generateNewArtistsForTests().getFirst();

        artistController.postRequestCreateArtist(artist);

        String response = artistController.getRequestGetAllArtistsByYearFounded(9999);

        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void getRequestGetAllArtistsByYearFounded_shouldReturnListOfAllArtistsByYearFounded() {
        Artist soloArtist = generateNewArtistsForTests().getFirst();
        artistController.postRequestCreateArtist(soloArtist);

        String response = artistController.getRequestGetAllArtistsByYearFounded(2000);

        assertEquals("Http 200 OK", response);
    }
    
    @Test
    void processGetArtistByIdRequest_shouldReturnNotFoundWhenArtistIdNotInDatabase() {
        Artist artist = generateNewArtistsForTests().getFirst();
        artist.setId(Integer.MAX_VALUE);

        String response = artistController.processGetArtistByIdRequest(artist.getId(), () -> "Http 200 OK");

        assertEquals("Http 404 Not Found", response);
    }

    @Test
    void processGetArtistByIdRequest_shouldFindArtistIdInDatabase() {
        Artist artist = generateNewArtistsForTests().getFirst();
        artistController.postRequestCreateArtist(artist);

        String response = artistController.processGetArtistByIdRequest(artist.getId(), () -> "Http 200 OK");

        assertEquals("Http 200 OK", response);
    }


    private static List<Artist> generateNewArtistsForTests() {
        return List.of(
                new Artist(2000, "US", "test 1 bio", ArtistType.SOLO, "Test 1"),
                new Artist(2020, "GB", "test 2 bio", ArtistType.SOLO, "Test 2"),
                new Artist(2005, "IE", "test 3 bio", ArtistType.GROUP, "Test 3"),
                new Artist(2025, "DE", "test 4 bio", ArtistType.SOLO, "Test 4")
        );
    }

}