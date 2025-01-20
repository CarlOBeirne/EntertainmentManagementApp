package com.pluralsight.service;

import java.util.List;
import java.util.Optional;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ArtistDataServiceTest {

    @Mock
    private DaoInterface<Artist> daoInterfaceMock;

    @InjectMocks
    private ArtistDataService artistDataService;

    private Artist buildTestArtist( int yearFounded, String nationality, String biography, ArtistType artistType, String name) {
        return new Artist( yearFounded, nationality, biography, artistType, name);
    }

    /** to test with Save
     * 1. Save new Artist
     * 2. Update existing artist
     * 3. Save null artist
     * 4. Save non-existing id
     */

    @Test
    public void TestSaveArtist_NewArtistCreated_CreationSuccess() {
        // given

        int yearFounded = 1990;
        String nationality = "UK";
        String biography = "The verve is a British band.";
        ArtistType artistType = ArtistType.GROUP;
        String name = "The Verve";

        Artist testArtist = buildTestArtist(yearFounded, nationality, biography, artistType, name);
        List<Track> tracks = List.of(
                new Track("Bittersweet Symphony", 600, Genre.ROCK, List.of(testArtist), 1995, 100)
        );

        // when
        daoInterfaceMock.save(testArtist);

        // then
        then (daoInterfaceMock).should().save(testArtist);
    }

    @Test
    public void TestSaveArtist_ExistingArtist_UpdateSuccess() {
        // given
        int yearFounded = 1990;
        String nationality = "UK";
        String biography = "The Verve is a British band.";
        ArtistType artistType = ArtistType.GROUP;
        String name = "The Verve";

        Artist testArtist = buildTestArtist(yearFounded, nationality, biography, artistType, name);
        testArtist.setId(10); // existing ID

        given(daoInterfaceMock.save(testArtist)).willReturn(Optional.of(testArtist));

        // when
        Optional<Artist> result = daoInterfaceMock.save(testArtist);

        // then
        assertTrue(result.isPresent(), "Artist should be updated successfully.");
        assertEquals(testArtist, result.get(), "Updated artist should match the input.");
        then(daoInterfaceMock).should().save(testArtist);
    }

    //null
    @Test
    public void TestSaveArtist_NullArtist_ThrowsException() {
        // given
        doThrow(new IllegalArgumentException("Artist cannot be null."))
                .when(daoInterfaceMock).save(null);

        // when, then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> daoInterfaceMock.save(null));
        assertEquals("Artist cannot be null.", exception.getMessage());
        then(daoInterfaceMock).should().save(null);
    }

    //Updating a nonexistent artist
    @Test
    public void TestSaveArtist_NonexistentArtist_SaveFails() {
        // given
        int yearFounded = 1990;
        String nationality = "UK";
        String biography = "The Verve is a British band.";
        ArtistType artistType = ArtistType.GROUP;
        String name = "The Verve";

        Artist testArtist = buildTestArtist(yearFounded, nationality, biography, artistType, name);
        testArtist.setId(999); // nonexistent id

        given(daoInterfaceMock.save(testArtist)).willReturn(Optional.empty());

        // when
        Optional<Artist> result = daoInterfaceMock.save(testArtist);

        // then
        assertTrue(result.isEmpty());
        then(daoInterfaceMock).should().save(testArtist);
    }

    /** to test with getByID(int id)
     * 1. Get existing artist
     * 2. Get a nonexistent artist
     */

    @Test
    public void TestGetById_whenIdExists_thenArtistReturned() {
        // given

        Artist testArtist = new Artist(
                1990,
                "UK",
                "The Verve is a British band.",
                ArtistType.GROUP,
                "The Verve"
        );

        when (daoInterfaceMock.getById(1)).thenReturn(Optional.of(testArtist));

        //when
        artistDataService.getArtistById(1);

        //then
        verify(daoInterfaceMock).getById(1);
    }

    @Test
    public void testGetById_NonExistentArtist_ReturnsEmpty() {
        // Given
        int testId = 42;
        given(daoInterfaceMock.getById(testId)).willReturn(Optional.empty());

        // When
        Optional<Artist> result = artistDataService.getArtistById(testId);

        // Then
        assertTrue(result.isEmpty(), "Expected result to be empty for a non-existent artist.");
        then(daoInterfaceMock).should().getById(testId);
    }


    //@Test
    //public void getById_shouldCallGetByIdMethod() {
    //    int id = 10;
//
    //    daoInterfaceMock.getById(id);
    //    verify(daoInterfaceMock).getById(anyInt());
    //}

    /** to test with getAll();
     * 1. Retrieve all artists when there are artists
     * 2. Try to retrieve artists when the collection is empty
     */

    @Test
    public void TestGetAllArtists_whenCalled_thenAllArtistsReturned() {
        // given
        List<Artist> artists = List.of(
                buildTestArtist(1990,
                        "UK",
                        "The Verve is a British band.",
                        ArtistType.GROUP,
                        "The Verve"),
                buildTestArtist(2000,
                        "FR",
                        "Daft Punk Bio",
                        ArtistType.GROUP,
                        "Daft ")
        );

        given(daoInterfaceMock.getAll()).willReturn(artists);

        // when
        List<Artist> result = artistDataService.getAllArtists();

        // then
        then(daoInterfaceMock).should().getAll();
    }

    @Test
    public void TestGetAllArtists_whenEmptyCollection_ReturnsEmptyList() {
        List<Artist> result = artistDataService.getAllArtists();
        assertTrue(result.isEmpty());
    }

    /** to test with delete(int id);
     * 1. delete existing artist
     * 2. delete a nonexisting artist
     */

    @Test
    public void TestDeleteArtist_whenIdExists_thenArtistDeleted() {
        // given

        Artist testArtist = new Artist(
                1990,
                "UK",
                "The Verve is a British band.",
                ArtistType.GROUP,
                "The Verve"
        );

        willDoNothing().given(daoInterfaceMock).delete(1);

        // when
        artistDataService.deleteArtistById(1);

        // then
        then(daoInterfaceMock).should().delete(1);
    }

    @Test
    public void TestDeleteNonexistentArtist_thenThrowsException() {
        // given
        int testId = 156;
        doThrow(new IllegalArgumentException("Artist ID " + testId + " does not exist."))
                .when(daoInterfaceMock).delete(testId);
        // when
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> artistDataService.deleteArtistById(testId),
                "Expected delete to throw an exception for non-existing artist."
        );

        // Assert the exception message
        assertEquals("Artist ID " + testId + " does not exist.", exception.getMessage());

        then(daoInterfaceMock).should().delete(testId);
    }
}
