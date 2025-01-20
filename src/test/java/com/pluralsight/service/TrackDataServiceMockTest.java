package com.pluralsight.service;

import com.pluralsight.dao.DaoInterface;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrackDataServiceMockTest {

    @Mock
    private DaoInterface<Track> daoInterface;

    @InjectMocks
    private TrackDataService trackDataService;

    @Captor
    ArgumentCaptor<Track> trackArgumentCaptor;

    @Test
    public void saveTrack_shouldCallSaveMethodIfTrackIsNotNull() {
        Artist artist = new Artist("1968", "English", "ABC", List.of(ArtistType.GROUP), "Deep Purple", 1);
        Track expectedTrack = new Track("Perfect Strangers", 328, Genre.ROCK,
                List.of(artist), 1984, 98);
        daoInterface.save(expectedTrack);
        verify(daoInterface, times(1)).save(expectedTrack);
    }

    @Test
    public void saveTrack_shouldCallSaveMethodEvenIfTrackIsNull() {
        daoInterface.save(null);
        verify(daoInterface, times(1)).save(null);
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
    public void getAll_shouldCallGetAll() {
    List<Track> allTracks = allTracks();
    when(daoInterface.getAll()).thenReturn(allTracks);
    daoInterface.getAll();
    verify(daoInterface, times(1)).getAll();
    }

    @Test
    public void getAll_shouldCallGetAllEvenIfNoTracks() {
        when(daoInterface.getAll()).thenReturn(null);
        daoInterface.getAll();
        verify(daoInterface, times(1)).getAll();
    }

    @Test
    public void delete_shouldCallDelete() {
        Artist artist = new Artist("1968", "English", "ABC", List.of(ArtistType.GROUP),
                "Deep Purple", 1);
        Track toDeleteTrack = new Track("Perfect Strangers", 328, Genre.ROCK,
                List.of(artist), 1984, 98);
        int id = 1;
        toDeleteTrack.setId(id);
        daoInterface.delete(id);
        verify(daoInterface, times(1)).delete(id);
        verify(daoInterface).delete(anyInt());
    }

    public List<Track> allTracks() {
        Artist artist1 = new Artist("1965", "English", "JKL", List.of(ArtistType.GROUP),
                "Pink Floyd", 1);
        Track track1 = new Track("High Hopes", 510, Genre.ROCK, List.of(artist1),
                1994,75);
        track1.setId(1);

        Artist artist2 = new Artist("1989", "American", "DEF", List.of(ArtistType.SOLO),
                "Nas", 2);
        Artist artist3 = new Artist("1990", "American", "GHI", List.of(ArtistType.SOLO),
                "Lauryn Hill", 3);
        Track track2 = new Track("If I Ruled The World", 282, Genre.HIP_HOP,
                List.of(artist2, artist3),1996,90);
        track2.setId(2);

        return List.of(track1, track2);
    }
}
