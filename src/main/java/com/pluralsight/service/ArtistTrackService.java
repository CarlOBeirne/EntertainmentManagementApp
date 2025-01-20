package com.pluralsight.service;

import com.pluralsight.dao.ArtistDAO;
import com.pluralsight.dao.TrackDAO;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;

import java.util.List;
import java.util.Optional;

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
        if (track.getId() == 0) {
            trackDataService.saveTrack(track).orElseThrow();

            linkTrackToArtists(track, track.getArtists().stream()
                    .map(Artist::getId)
                    .toList());

            unlinkTrackFromArtists(track, track.getArtists().stream()
                    .map(Artist::getId)
                    .toList());

            linkGenreFromTrackToArtists(track, track.getArtists().stream()
                    .map(Artist::getId)
                    .toList());
        } else {
            trackDataService.saveTrack(track).orElseThrow();
        }
        return Optional.of(track);
    }

    public Optional<Track> getTrackById(int trackId) {
        return trackDataService.getByTrackId(trackId);
    }

    public List<Track> getAllTracks() {
        return trackDataService.getAllTracks();
    }

    public void deleteTrack(int trackId) {
        trackDataService.deleteTrack(trackId);
    }

    public List<Track> getByTrackName(String title) {
        return trackDataService.getByTrackName(title);
    }

    public List<Track> getByTrackGenre(Genre genre) {
        return trackDataService.getByTrackGenre(genre);
    }

    public List<Track> getByTrackYearReleased(int yearReleased) {
        return trackDataService.getByTrackYearReleased(yearReleased);
    }

    public List<Track> getByTrackArtist(Artist artist) {
        return trackDataService.getByTrackArtist(artist);
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
        checkArtistIdAndTrackHaveData(track, artistId);

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
        artistIdList.forEach(artistId -> linkTrackToArtist(track, artistId));
    }

    private void unlinkTrackFromArtists(Track track, List<Integer> artistIdList) {
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

    private void linkGenreFromTrackToArtist(Track track, Integer artistId) {
        checkArtistIdAndTrackHaveData(track, artistId);
        Optional<Artist> optionalArtist = artistDataService.getArtistById(artistId);
        if (optionalArtist.isEmpty()) {
            throw new IllegalArgumentException("Artist id not found.");
        }
        Artist artist = optionalArtist.get();
        if (!artist.getGenres().contains(track.getGenre())) {
            artist.addGenre(track.getGenre());
        }
    }

    private void linkGenreFromTrackToArtists(Track track, List<Integer> artistIdList) {
        if (artistIdList == null || artistIdList.isEmpty()) {
            throw new IllegalArgumentException("Invalid artistIdList");
        } else if (track == null) {
            throw new IllegalArgumentException("Invalid track");
        }
        artistIdList.forEach(artistId -> linkGenreFromTrackToArtist(track, artistId));
    }

    private static void checkArtistIdAndTrackHaveData(Track track, Integer artistId) {
        if (artistId == null) {
            throw new NullPointerException("Artist id is null.");
        } else if (track == null) {
            throw new NullPointerException("Track is null.");
        }
    }

}
