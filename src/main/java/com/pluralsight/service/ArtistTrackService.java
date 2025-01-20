package com.pluralsight.service;

import com.pluralsight.dao.ArtistDAO;
import com.pluralsight.dao.TrackDAO;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArtistTrackService {
    private static ArtistDataService artistDataService;
    private static TrackDataService trackDataService;

    public ArtistTrackService() {
        if (ArtistTrackService.artistDataService == null) {
            ArtistTrackService.artistDataService = new ArtistDataService(new ArtistDAO());
        }
        if (ArtistTrackService.trackDataService == null) {
            ArtistTrackService.trackDataService = new TrackDataService(new TrackDAO());
        }
    }

    public Optional<Track> saveTrack(Track track) {
        if (track == null) throw new IllegalArgumentException("Track is null.");
        Track persistedTrack = trackDataService.saveTrack(track).orElseThrow();
        linkTrackToArtists(persistedTrack, persistedTrack.getArtists().stream()
                .map(Artist::getId)
                .collect(Collectors.toList()));

        unlinkTrackFromArtists(track, track.getArtists().stream()
                .map(Artist::getId)
                .collect(Collectors.toList()));

        return Optional.of(persistedTrack);
    }

    public Optional<Artist> saveArtist(Artist artist) {
        if (artist == null) {
            throw new IllegalArgumentException("Track cannot be null");
        }

        return artistDataService.saveArtist(artist);
    }

    public Optional<Artist> getArtistById(int aritstId) {
        return artistDataService.getArtistById(aritstId);
    }

    public List<Artist> getAllArtists() {
        return artistDataService.getAllArtists();
    }

    public void deleteArtistById(int id) {
        artistDataService.deleteArtistById(id);
    }

    public List<Artist> getByName(String name) {
        return artistDataService.getByName(name);
    }

    public List<Artist> getByArtistGenre(Genre genre) {
        return artistDataService.getByArtistGenre(genre);
    }

    public List<Artist> getByArtistNationality(String nationality) {
        return artistDataService.getByArtistNationality(nationality);
    }

    public List<Artist> getByArtistYearFounded(int yearFounded) {
        return artistDataService.getByArtistYearFounded(yearFounded);
    }

    public List<Artist> getByArtistType(ArtistType artistType) {
        return artistDataService.getByArtistType(artistType);
    }

    public List<Artist> getByArtistTrackId(int trackId) {
        return artistDataService.getByArtistTrackId(trackId);
    }

    public List<Artist> getByArtistBiography(String biography) {
        return artistDataService.getByArtistBiography(biography);
    }

    private void linkTrackToArtist(Track track, Integer artistId) {
        if (artistId == null) {
            throw new NullPointerException("Artist id is null.");
        } else if (track == null) {
            throw new NullPointerException("Track is null.");
        }

        Optional<Artist> optionalArtist = artistDataService.getArtistById(artistId);
        if (optionalArtist.isEmpty()) {
            throw new IllegalArgumentException("Artist id not found.");
        }
        Artist artist = optionalArtist.get();
        artist.addTrack(track);
    }

    private void linkTrackToArtists(Track track, List<Integer> artistIdList) {
        if (artistIdList == null || artistIdList.isEmpty()) {
            throw new IllegalArgumentException("Invalid artistIdList");
        } else if (track == null) {
            throw new IllegalArgumentException("Invalid track");
        }
        artistIdList.forEach(artist -> linkTrackToArtist(track, artist));
    }

    public void unlinkTrackFromArtists(Track track, List<Integer> artistIdList) {
        if (artistIdList == null || artistIdList.isEmpty()) {
            throw new IllegalArgumentException("Invalid artistIdList");
        } else if (track == null) {
            throw new IllegalArgumentException("Invalid track");
        }
        // Fetch all existing artists associate to a track
        List<Artist> artistsByTrack = artistDataService.getByArtistTrackId(track.getId());
        List<Artist> artistsToRemoveTrackFrom = artistsByTrack.stream()
                .filter(artist -> !artistIdList.contains(artist.getId()))
                .toList();
        artistsToRemoveTrackFrom.forEach(artist -> artist.removeTrack(track));

    }


}
