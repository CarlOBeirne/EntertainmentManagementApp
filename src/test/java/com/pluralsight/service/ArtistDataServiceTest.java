package com.pluralsight.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void testSaveArtist_NewArtistCreated_CreationSuccess() {

        int yearFounded = 1990;
        String nationality = "UK";
        String biography = "The verve is a British band.";
        ArtistType artistType = ArtistType.GROUP;
        String name = "The Verve";

        Artist testArtist = buildTestArtist(yearFounded, nationality, biography, artistType, name);
        List<Track> tracks = List.of(
                new Track("Bittersweet Symphony", 600, Genre.ROCK, List.of(testArtist), 1995, 100)
        );
        daoInterfaceMock.save(testArtist);
        then (daoInterfaceMock).should().save(testArtist);
    }

    @Test
    public void testSaveArtist_ExistingArtist_UpdateSuccess() {
        int yearFounded = 1990;
        String nationality = "UK";
        String biography = "The Verve is a British band.";
        ArtistType artistType = ArtistType.GROUP;
        String name = "The Verve";

        Artist testArtist = buildTestArtist(yearFounded, nationality, biography, artistType, name);
        testArtist.setId(1);

        given(daoInterfaceMock.save(testArtist)).willReturn(Optional.of(testArtist));
        Optional<Artist> result = daoInterfaceMock.save(testArtist);
        assertTrue(result.isPresent(), "Artist should be updated successfully.");
        assertEquals(testArtist, result.get(), "Updated artist should match the input.");
        then(daoInterfaceMock).should().save(testArtist);
    }

    //null
    @Test
    public void testSaveArtist_NullArtist_ThrowsException() {
        doThrow(new IllegalArgumentException("Artist cannot be null."))
                .when(daoInterfaceMock).save(null);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> daoInterfaceMock.save(null));
        assertEquals("Artist cannot be null.", exception.getMessage());
        then(daoInterfaceMock).should().save(null);
    }

    //Updating a nonexistent artist
    @Test
    public void testSaveArtist_NonexistentArtist_SaveFails() {
        int yearFounded = 1990;
        String nationality = "UK";
        String biography = "The Verve is a British band.";
        ArtistType artistType = ArtistType.GROUP;
        String name = "The Verve";

        Artist testArtist = buildTestArtist(yearFounded, nationality, biography, artistType, name);
        testArtist.setId(999); // nonexistent id

        given(daoInterfaceMock.save(testArtist)).willReturn(Optional.empty());
        Optional<Artist> result = daoInterfaceMock.save(testArtist);
        assertTrue(result.isEmpty());
        then(daoInterfaceMock).should().save(testArtist);
    }

    /** to test with getByID(int id)
     * 1. Get existing artist
     * 2. Get a nonexistent artist
     */

    @Test
    public void testGetById_whenIdExists_thenArtistReturned() {
        Artist testArtist = new Artist(
                1990,
                "UK",
                "The Verve is a British band.",
                ArtistType.GROUP,
                "The Verve"
        );

        when (daoInterfaceMock.getById(1)).thenReturn(Optional.of(testArtist));
        artistDataService.getArtistById(1);
        verify(daoInterfaceMock).getById(1);
    }

    @Test
    public void testGetById_NonExistentArtist_ReturnsEmpty() {
        int testId = 42;
        given(daoInterfaceMock.getById(testId)).willReturn(Optional.empty());
        Optional<Artist> result = artistDataService.getArtistById(testId);
        assertTrue(result.isEmpty(), "Expected result to be empty for a non-existent artist.");
        then(daoInterfaceMock).should().getById(testId);
    }

    /** to test with getAll();
     * 1. Retrieve all artists when there are artists
     * 2. Try to retrieve artists when the collection is empty
     */

    @Test
    public void testGetAllArtists_whenCalled_thenAllArtistsReturned() {
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
        List<Artist> result = artistDataService.getAllArtists();
        then(daoInterfaceMock).should().getAll();
    }

    @Test
    public void testGetAllArtists_whenEmptyCollection_ReturnsEmptyList() {
        List<Artist> result = artistDataService.getAllArtists();
        assertTrue(result.isEmpty());
    }

    /** to test with delete(int id);
     * 1. delete existing artist
     * 2. delete a nonexisting artist
     */

    @Test
    public void testDeleteArtist_whenIdExists_thenArtistDeleted() {
        Artist testArtist = new Artist(
                1990,
                "UK",
                "The Verve is a British band.",
                ArtistType.GROUP,
                "The Verve"
        );
        willDoNothing().given(daoInterfaceMock).delete(1);
        artistDataService.deleteArtistById(1);
        then(daoInterfaceMock).should().delete(1);
    }

    @Test
    public void testDeleteNonexistentArtist_thenThrowsException() {
        int testId = 156;
        doThrow(new IllegalArgumentException("Artist ID " + testId + " does not exist."))
                .when(daoInterfaceMock).delete(testId);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> artistDataService.deleteArtistById(testId),
                "Expected delete to throw an exception for non-existing artist."
        );
        assertEquals("Artist ID " + testId + " does not exist.", exception.getMessage());
        then(daoInterfaceMock).should().delete(testId);
    }

    @Test
    public void testGetByName_MatchingArtistsReturned() {
        String searchName = "verve";
        List<Artist> mockArtists = List.of(
                new Artist(1990, "UK", "The Verve is a British band.", ArtistType.GROUP, "The Verve"),
                new Artist(1234, "USA", "Amazing Rockstar", ArtistType.SOLO, "Verve Test"),
                new Artist(2000, "USA", "Legend.", ArtistType.SOLO, "Jimmy Hendrix"));

        given(daoInterfaceMock.getAll()).willReturn(mockArtists);
        List<Artist> result = artistDataService.getByName(searchName);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(artist -> artist.getName().toLowerCase().contains(searchName)));
        verify(daoInterfaceMock).getAll();
    }

    @Test
    public void testGetByArtistNationality_MatchingArtistsReturned() {
        String searchNationality = "USA";
        List<Artist> mockArtists = List.of(
                new Artist(1990, "UK", "The Verve is a British band.", ArtistType.GROUP, "The Verve"),
                new Artist(1234, "USA", "Amazing Rockstar", ArtistType.SOLO, "Verve Test"),
                new Artist(2000, "USA", "Legend.", ArtistType.SOLO, "Jimmy Hendrix"));

        given(daoInterfaceMock.getAll()).willReturn(mockArtists);
        List<Artist> result = artistDataService.getByArtistNationality(searchNationality);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(artist -> artist.getNationality().equalsIgnoreCase(searchNationality)));
        verify(daoInterfaceMock).getAll();
    }


    @Test
    public void testGetByArtistYearFounded_MatchingArtistsReturned() {
        int searchYearFounded = 2000;
        List<Artist> mockArtists = List.of(
                new Artist(1990, "UK", "The Verve is a British band.", ArtistType.GROUP, "The Verve"),
                new Artist(1234, "USA", "Amazing Rockstar", ArtistType.SOLO, "Verve Test"),
                new Artist(2000, "USA", "Legend.", ArtistType.SOLO, "Jimmy Hendrix"));

        given(daoInterfaceMock.getAll()).willReturn(mockArtists);
        List<Artist> result = artistDataService.getByArtistYearFounded(searchYearFounded);
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(artist -> artist.getYearFounded() == (searchYearFounded)));
        verify(daoInterfaceMock).getAll();
    }

    @Test
    public void testGetByArtistType_MatchingArtistsReturned() {
        ArtistType searchArtistType = ArtistType.SOLO;
        List<Artist> mockArtists = List.of(
                new Artist(1990, "UK", "The Verve is a British band.", ArtistType.GROUP, "The Verve"),
                new Artist(1234, "USA", "Amazing Rockstar", ArtistType.SOLO, "Verve Test"),
                new Artist(2000, "USA", "Legend.", ArtistType.SOLO, "Jimmy Hendrix"));

        given(daoInterfaceMock.getAll()).willReturn(mockArtists);
        List<Artist> result = artistDataService.getByArtistType(searchArtistType);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(artist -> artist.getArtistType() == (searchArtistType)));
        verify(daoInterfaceMock).getAll();
    }

    @Test
    public void testGetByArtistBiography_MatchingArtistsReturned() {
        String searchBiography = "Rockstar";
        List<Artist> mockArtists = List.of(
                new Artist(1990, "UK", "The Verve is a British band.", ArtistType.GROUP, "The Verve"),
                new Artist(1234, "USA", "Amazing Rockstar", ArtistType.SOLO, "Verve Test"),
                new Artist(2000, "USA", "Legend.", ArtistType.SOLO, "Jimmy Hendrix"));

        given(daoInterfaceMock.getAll()).willReturn(mockArtists);
        List<Artist> result = artistDataService.getByArtistBiography(searchBiography);
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(artist -> artist.getBiography().contains(searchBiography)));
        verify(daoInterfaceMock).getAll();
    }

    @Test
    public void testGetByArtistTrackId_MatchingArtistsReturned() {
        int searchArtistTrackID = 5;

        Artist artist1 = new Artist(1990, "UK", "The Verve is a British band.", ArtistType.GROUP, "The Verve");
        Artist artist2 = new Artist(1234, "USA", "Amazing Rockstar", ArtistType.SOLO, "Verve Test");
        Artist artist3 = new Artist(2000, "USA", "Legend.", ArtistType.SOLO, "Jimmy Hendrix");

        Track track1 = new Track("Heaven", 300, Genre.ROCK, List.of(artist1),1955,75);
        Track track2 = new Track("DemoTrack", 400, Genre.ROCK, List.of(artist2),1990,75);

        track1.setId(5);
        track2.setId(2);
        artist1.setId(1);
        artist2.setId(2);

        artist1.setTracks(List.of(track1));
        artist2.setTracks(List.of(track2));
        artist3.setTracks(List.of());

        List<Artist> mockArtists = List.of(artist1, artist2, artist3);

        when(daoInterfaceMock.getAll()).thenReturn(mockArtists);
        List<Artist> result = artistDataService.getByArtistTrackId(searchArtistTrackID);
        assertEquals(1, result.size());
        //assertEquals(result.stream().allMatch(artist -> artist.getId() == (searchArtistTrackID)));
        assertEquals(artist1, result.getFirst());
        verify(daoInterfaceMock).getAll();
    }

    @Test
    public void testSaveArtist_NewArtist_AssignsNewIdAndSaves() {

        Artist nullArtist = null;
        Artist artistWithIdZero = new Artist(0, "USA", "Legend.", ArtistType.SOLO, "Jimmy Hendrix");
        Artist savedArtist = new Artist(1, "USA", "Legend.", ArtistType.SOLO, "Jimmy Hendrix");
        given(daoInterfaceMock.save(artistWithIdZero)).willReturn(Optional.of(savedArtist));

        Optional<Artist> result = artistDataService.saveArtist(artistWithIdZero);

        assertTrue(result.isPresent());
        assertEquals(savedArtist, result.get());
        verify(daoInterfaceMock).save(artistWithIdZero);
    }

    @Test
    public void testSaveArtist_NullArtist() {
        Artist nullArtist = null;
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> artistDataService.saveArtist(nullArtist));
        assertEquals("Artist is null.", exception.getMessage());
        verify(daoInterfaceMock, never()).save(any());
    }

}
