package com.pluralsight.service;

import com.pluralsight.dao.DaoInterface;
import com.pluralsight.dao.TrackDAO;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrackDataServiceTest {

    @Mock
    private DaoInterface<Track> daoInterface;

    @InjectMocks
    private TrackDataService trackDataService;

    @Captor
    ArgumentCaptor<Track> trackArgumentCaptor;

    @Test
    public void saveTrack_shouldCallSaveMethod() {
        Track expectedTrack = new Track("Perfect Strangers", 328, Genre.ROCK,
                List.of("Deep Purple"), 1984, 98);
        daoInterface.save(expectedTrack);
        verify(daoInterface, times(1)).save(expectedTrack);
    }

    public List<Track> allTracks() {
        Track track1 = new Track("High Hopes", 510, Genre.ROCK, List.of("Pink Floyd"), 1994,
                75);
        Track track2 = new Track("If I Ruled The World", 282, Genre.HIP_HOP, List.of("Nas",
                "Lauryn Hill"), 1996, 90);
        return List.of(track1, track2);
    }
}
