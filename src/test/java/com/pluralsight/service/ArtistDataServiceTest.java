package com.pluralsight.service;

import java.util.List;
import java.util.Optional;

import com.pluralsight.dao.ArtistDAO;
import com.pluralsight.dao.DaoInterface;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ArtistDataServiceTest {

    @Mock
    private DaoInterface<Artist> daoInterfaceMock;

    @InjectMocks
    private ArtistDataService artistDataService;

    private Artist buildTestArtist( String yearFounded, String nationality, String biography, List<ArtistType> artistTypes, String name, int id) {
        Artist artist = new Artist(yearFounded, nationality,biography,artistTypes,name,id);
        artist.setId(id);
        return artist;
    }

    @Test
    public void TestSaveArtist_NewArtistCreated_CreationSuccess() {
        //Given

        List<ArtistType> artistTypes = List.of(ArtistType.GROUP);
        String yearFounded = "1990";
        String nationality = "UK";
        String biography = "The verve is a British band.";
        String name = "The Verve";
        int id = 0;

        Artist testArtist = buildTestArtist(yearFounded, nationality, biography, artistTypes, name, id);
        List<Track> tracks = List.of(
                new Track("Bittersweet Symphony", 600, Genre.ROCK, List.of(testArtist), 1995, 100)
        );

        //var testArtist = buildTestArtist(tracks, yearFounded, nationality, biography, genres, artistTypes, name, id);

        //when
        daoInterfaceMock.save(testArtist);

        //then
        then (daoInterfaceMock).should().save(testArtist);

    }

    @Test
    public void TestGetById_whenIdExists_thenArtistReturned() {
        // Given

        Artist testArtist = new Artist(
                "1990",
                "UK",
                "The Verve is a British band.",
                List.of(ArtistType.GROUP),
                "The Verve",
                20
        );

        //when (daoInterfaceMock.getById(20)).thenReturn(Optional.of(testArtist));

        // When
        artistDataService.getArtistById(20);

        // Then
        verify(daoInterfaceMock).getById(20);

    }

    @Test
    void testGetArtistById() {

        Artist testArtist = new Artist(
                "1990",
                "UK",
                "The Verve is a British band.",
                List.of(ArtistType.GROUP),
                "The Verve",
                1
        );

        when(daoInterfaceMock.getById(1)).thenReturn(Optional.of(testArtist));
        //Optional<Artist> result = artistDataService.getArtistById(1);
        verify(daoInterfaceMock, times(1)).getById(1);
    }

    @Test
    public void getById_shouldCallGetByIdMethod() {
        int id = 10;

        daoInterfaceMock.getById(id);
        verify(daoInterfaceMock).getById(anyInt());
    }


    @Test
    public void TestGetAllArtists_whenCalled_thenAllArtistsReturned() {
        // Given
        List<Artist> artists = List.of(
                buildTestArtist( "1990", "UK", "The Verve biography", List.of(ArtistType.GROUP), "The Verve", 1),
                buildTestArtist( "1980", "USA", "Another artist biography", List.of(ArtistType.SOLO), "John Doe", 2)
        );

        given(daoInterfaceMock.getAll()).willReturn(artists);

        // When
        List<Artist> result = artistDataService.getAllArtists();

        // Then
        //assertEquals(artists.size(), result.size(), "Expected list size does not match");
        //assertEquals(artists, result, "Expected artists list does not match");
        then(daoInterfaceMock).should().getAll();
    }


    @Test
    public void TestDeleteArtist_whenIdExists_thenArtistDeleted() {
        // Given
        int testId = 10;
        Artist testArtist = new Artist(
                "1990",
                "UK",
                "The Verve is a British band.",
                List.of(ArtistType.GROUP),
                "The Verve",
                testId
        );

        willDoNothing().given(daoInterfaceMock).delete(testId);

        // When
        artistDataService.deleteArtistById(testId);

        // Then
        then(daoInterfaceMock).should().delete(testId);
    }

    /**@Test
    public void TestGetById_whenIdExists_thenArtistReturned() {
    //given

    int testId = 20;
    Artist testArtist = new Artist(
    List.of(),
    "1990",
    "UK",
    "The Verve is a british band",
    List.of(Genre.ROCK),
    List.of(ArtistType.GROUP),
    "The Verve",
    testId
    );

    given(artistDAOMock.getById(testId)).willReturn(Optional.of(testArtist));

    // when
    Optional<Artist> result = artistDataService.getArtistById(testId);

    //then
    assertTrue(result.isPresent(), "Expected artist to be present");
    assertEquals(testArtist, result.get(), "Expected artist does not match");
    then(artistDAOMock).should().getById(testId);

    }
     */



    //    this.yearFounded = yearFounded;
    //    this.nationality = nacionality;
    //    this.biography = biography;
    //    this.artistTypes = artistTypes;
    //    this.name = name;
    //    this.id = id

    /**
     * public Artist saveArtist(Artist artist) {
     //    if (artist == null) {
     //        System.err.println("NullPointerException on SaveArtist.");
     //    }
     //    return artistDAO.save(artist);
     //     }
     */
/**
 *     public Artist(List<Track> tracks, String yearFounded, String nacionality, String biography, List<Genre> genres, List<ArtistType> artistTypes, String name, int id) {
 *         this.tracks = tracks;
 *         this.yearFounded = yearFounded;
 *         this.nacionality = nacionality;
 *         this.biography = biography;
 *         this.genres = genres;
 *         this.artistTypes = artistTypes;
 *         this.name = name;
 *         this.id = id;
 *     }
 */


}
