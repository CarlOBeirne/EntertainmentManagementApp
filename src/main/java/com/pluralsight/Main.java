package com.pluralsight;

import com.pluralsight.controller.ArtistController;
import com.pluralsight.controller.TrackController;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;
import com.pluralsight.service.ArtistTrackService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        init();
    }

    public static void init() {
        ArtistController artistController = new ArtistController(new ArtistTrackService());
        TrackController trackController = new TrackController(new ArtistTrackService());

        Artist coldplay = new Artist(1996, "GB",
                "Coldplay are a British rock band formed in London in 1997.",
                ArtistType.GROUP,
                "Coldplay");
        Artist bts = new Artist(2010, "KR",
                "BTS, also known as the Bangtan Boys, is a South Korean band formed in 2010",
                ArtistType.GROUP,
                "BTS");

        artistController.postRequestCreateArtist(coldplay);
        artistController.postRequestCreateArtist(bts);

        Track vivaLaVida = new Track("Viva la Vida", 225, Genre.ROCK, List.of(coldplay), 2008,138);
        Track myUniverse = new Track("My Universe", 229, Genre.ROCK, List.of(coldplay, bts), 2008,138);

        trackController.createTrack(vivaLaVida);
        trackController.createTrack(myUniverse);

        artistController.getRequestGetAllArtists();
        trackController.getAllTracks();

        coldplay.setYearFounded(1997);
        artistController.putRequestUpdateArtist(coldplay.getId(), coldplay);

        artistController.getRequestGetAllArtists();

        trackController.deleteTrackById(myUniverse.getId());

        artistController.getRequestGetAllArtistsByNationality("gb");

    }
}