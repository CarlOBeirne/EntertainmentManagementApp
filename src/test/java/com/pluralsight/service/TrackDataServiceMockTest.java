package com.pluralsight.service;

import com.pluralsight.dao.DaoInterface;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TrackDataServiceMockTest {

    @Mock
    private DaoInterface<Track> daoInterface;

    @InjectMocks
    private TrackDataService trackDataService;

    @Test
    public void saveTrack_shouldCallSaveMethodIfTrackIsNotNull() {
        Artist artist = new Artist(1968, "English", "ABC", ArtistType.GROUP, "Deep Purple");
        Track expectedTrack = new Track("Perfect Strangers", 328, Genre.ROCK,
                List.of(artist), 1984, 98);
        trackDataService.saveTrack(expectedTrack);
        verify(daoInterface, times(1)).save(expectedTrack);
    }

    @Test
    public void saveTrack_shouldCallSaveMethodEvenIfTrackIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> trackDataService.saveTrack(null));
        verify(daoInterface, never()).save(null);
    }

    @Test
    public void getById_shouldCallGetByIdMethod() {
        List<Track> allTracks = allTracks();
        int id = 1;
        when(daoInterface.getById(id)).thenReturn(Optional.ofNullable(allTracks.get(id)));
        trackDataService.getByTrackId(id);
        verify(daoInterface).getById(id);
    }

    @Test
    public void getById_shouldCallGetByIdMethodAndReturnEmptyOptionalWhenIdDoesNotExist() {
        int id = 10;
        when(daoInterface.getById(id)).thenReturn(Optional.empty());
        Optional<Track> actualTrack = trackDataService.getByTrackId(id);
        verify(daoInterface).getById(id);
        assertEquals(Optional.empty(), actualTrack);
    }

    @Test
    public void getAll_shouldCallGetAllAndReturnAllTracks() {
    List<Track> allTracks = allTracks();
    when(daoInterface.getAll()).thenReturn(allTracks);
    List<Track> actualTracks = trackDataService.getAllTracks();
    verify(daoInterface, times(1)).getAll();
    assertEquals(allTracks, actualTracks);
    }

    @Test
    public void getAll_shouldCallGetAllEvenIfNoTracksAndReturnEmptyList() {
        when(daoInterface.getAll()).thenReturn(new ArrayList<>());
        List<Track> actualTracks = trackDataService.getAllTracks();
        verify(daoInterface, times(1)).getAll();
        assertEquals(new ArrayList<>(), actualTracks);
    }

    @Test
    public void delete_shouldCallDeleteForValidId() {
        Track toDeleteTrack = new Track("Perfect Strangers", 328, Genre.ROCK,
                null, 1984, 98);
        int id = 1;
        toDeleteTrack.setId(id);
        trackDataService.deleteTrack(id);
        verify(daoInterface, times(1)).delete(id);
        verify(daoInterface).delete(anyInt());
    }

    @Test
    public void delete_shouldCallDeleteForAnyId() {
        int id = anyInt();
        trackDataService.deleteTrack(id);
        verify(daoInterface, times(1)).delete(id);
    }

    public List<Track> allTracks() {
        Artist artist1 = new Artist(1965, "English", "JKL", ArtistType.GROUP,
                "Pink Floyd");
        Track track1 = new Track("High Hopes", 510, Genre.ROCK, List.of(artist1),
                1994,75);
        track1.setId(1);

        Artist artist2 = new Artist(1989, "American", "DEF", ArtistType.SOLO,
                "Nas");
        Artist artist3 = new Artist(1990, "American", "GHI", ArtistType.SOLO,
                "Lauryn Hill");
        Track track2 = new Track("If I Ruled The World", 282, Genre.HIP_HOP,
                List.of(artist2, artist3),1996,90);
        track2.setId(2);

        return List.of(track1, track2);
    }

    @Test
    public void getByTrackName_shouldThrowIllegalArgumentExceptionIfTrackIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> trackDataService.getByTrackName(null));
        verify(daoInterface, never()).getAll();
    }

    @Test
    public void getByTrackName_shouldCallGetAllTracksOnce() {
        when(daoInterface.getAll()).thenReturn(allTracks());
        trackDataService.getByTrackName("High Hopes");
        verify(daoInterface, times(1)).getAll();
    }

    @Test
    public void getByTrackName_shouldGetTitle() {
        when(daoInterface.getAll()).thenReturn(allTracks());
        String actualTrack = trackDataService.getByTrackName("High Hopes").getFirst().getTitle();
        assertEquals("High Hopes", actualTrack);
    }

    @Test
    public void getByTrackName_shouldSwitchToLowerCase() {
        Artist artist1 = new Artist(1965, "English", "JKL", ArtistType.GROUP,
                "Pink Floyd");
        Track track1 = new Track("HIgH hOPES", 510, Genre.ROCK, List.of(artist1),
                1994,75);
        Track track2 = new Track("HIGH HOPES", 510, Genre.ROCK, List.of(artist1),
                1994,75);

        List<Track> allTracks = Arrays.asList(track1, track2);
        when(daoInterface.getAll()).thenReturn(allTracks);
        String title = "High Hopes";
        List<Track> actualTrack = trackDataService.getByTrackName(title);
        Assertions.assertAll(
                () -> assertEquals(2, actualTrack.size()),
                () -> assertTrue(allTracks.contains(track1)),
                () -> assertTrue(allTracks.contains(track2))
        );
    }

    @Test
    public void getByTrackGenre_shouldThrowIllegalArgumentExceptionIfGenreIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> trackDataService.getByTrackGenre(null));
        verify(daoInterface, never()).getAll();
    }

    @Test
    public void getByTrackGenre_shouldCallGetAllTracksOnce() {
        when(daoInterface.getAll()).thenReturn(allTracks());
        trackDataService.getByTrackGenre(Genre.ROCK);
        verify(daoInterface, times(1)).getAll();
    }

    @Test
    public void getByTrackGenre_shouldGetGenre() {
        when(daoInterface.getAll()).thenReturn(allTracks());
        Genre genre = trackDataService.getByTrackGenre(Genre.ROCK).getFirst().getGenre();
        Assertions.assertEquals(Genre.ROCK, genre);
    }

    @Test
    public void getByTrackYearReleased_shouldThrowIllegalArgumentExceptionIfYearReleasedIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> trackDataService.getByTrackYearReleased(-5));
        verify(daoInterface, never()).getAll();
    }

    @Test
    public void getByTrackYearReleased_shouldCallGetAllTracksOnce() {
        when(daoInterface.getAll()).thenReturn(allTracks());
        trackDataService.getByTrackYearReleased(1996);
        verify(daoInterface, times(1)).getAll();
    }

    @Test
    public void getByTrackYearReleased_shouldGetYearReleased() {
        when(daoInterface.getAll()).thenReturn(allTracks());
        int yearReleased = trackDataService.getByTrackYearReleased(1996).getFirst().getYearReleased();
        Assertions.assertEquals(1996, yearReleased);
    }

    @Test
    public void getByTrackArtist_shouldThrowIllegalArgumentExceptionIfArtistIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> trackDataService.getByTrackArtist(null));
        verify(daoInterface, never()).getAll();
    }

    @Test
    public void getByTrackArtist_shouldCallGetAllTracksOnce() {
        Artist artist1 = new Artist(1965, "English", "JKL", ArtistType.GROUP,
                "Pink Floyd");
        Track track1 = new Track("High Hopes", 510, Genre.ROCK, List.of(artist1),
                1994,75);

        trackDataService.getByTrackArtist(artist1);
        verify(daoInterface, times(1)).getAll();
    }

    @Test
    public void getByTrackArtist_shouldGetArtists() {
        Artist artist1 = new Artist(1965, "English", "JKL", ArtistType.GROUP,
                "Pink Floyd");
        Track track1 = new Track("High Hopes", 510, Genre.ROCK, List.of(artist1),
                1994,75);
        Track track2 = new Track("Comfortably Numb", 410, Genre.ROCK, List.of(artist1),
                1998,80);
        track1.setArtists(List.of(artist1));
        track2.setArtists(List.of(artist1));
        when(daoInterface.getAll()).thenReturn(List.of(track1, track2));
        List<Track> expectedTrackList = List.of(track1, track2);
        List<Track> actualTrackList = trackDataService.getByTrackArtist(artist1);
        Assertions.assertEquals(expectedTrackList, actualTrackList);
    }
}
