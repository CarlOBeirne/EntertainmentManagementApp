package com.pluralsight.controller;

import com.pluralsight.dao.TrackDAO;
import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.Genre;
import com.pluralsight.service.ArtistTrackService;
import com.pluralsight.service.TrackDataService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class TrackController {

    private final ArtistTrackService artistTrackService;

    public TrackController(ArtistTrackService artistTrackService) {
        this.artistTrackService = artistTrackService;
    }

    public Optional<Track> saveTrack(Track track) {
        return artistTrackService.saveTrack(track);
    }

    public String getTrackById (int id) {
        return checkTrackId(id, () -> "http 200 - OK");
    }

    public String getAllTracks () {
        try {
            List<Track> trackList = artistTrackService.getAllTracks();
            if (trackList.isEmpty()) {
                System.out.println("No tracks founded");
                return "Http 404 - Not Found";
            }
            System.out.println(trackList);
            return "Http 200 - OK";
        } catch (Exception e) {
            return "Http 500 - Internal Server Error. " + e.getMessage();
        }
    }

    public String deleteTrack (int id) {
        if (id <= 0) return "Http 404 - Not Found";
        return checkTrackId(id, () -> {
            artistTrackService.deleteTrack(id);
            return "Http 200 - OK";
        });
    }

    public String getTrackByName (String name) {
        if (name == null || name.isBlank()) return "Http 404 - Not Found";
        try {
            List<Track> trackList = artistTrackService.getByTrackName(name);
            if (trackList.isEmpty()) {
                System.out.println("No tracks founded");
                return "Http 404 - Not Found";
            }
            trackList.forEach(System.out::println);
            return "Http 200 - OK";
        } catch (Exception e) {
            return "Http 500 - Internal Server Error. " + e.getMessage();
        }    }

    public String getTrackByGenre(Genre genre) {
        if (genre == null) return "Http 404 - Not Found";
        try {
            List<Track> trackList = artistTrackService.getByTrackGenre(genre);
            if (trackList.isEmpty()) {
                System.out.println("No tracks founded");
                return "Http 404 - Not Found";
            }
            trackList.forEach(System.out::println);
            return "Http 200 - OK";
        } catch (Exception e) {
            System.out.println();
            return "Http 500 - Internal Server Error. " + e.getMessage();
        }
    }

    public String getTrackByYearReleased(int yearReleased) {
        if (yearReleased < 0) return "Http 404 - Not Found";
        try {
            List<Track> trackList = artistTrackService.getByTrackYearReleased(yearReleased);
            if (trackList.isEmpty()) {
                System.out.println("No tracks founded");
                return "Http 404 - Not Found";
            }
            trackList.forEach(System.out::println);
            return "Http 200 - OK";
        } catch (Exception e) {
            return "Http 500 - Internal Server Error. " + e.getMessage();
        }
    }

    public String getTrackByArtist (Artist artist){
        if (artist == null) return "Http 404 - Not Found";
        try {
            List<Track> trackList = artistTrackService.getByTrackArtist(artist);
            if (trackList.isEmpty()) {
                System.out.println("No tracks founded");
                return "Http 404 - Not Found";
            }
            trackList.forEach(System.out::println);
            return "Http 200 - OK";
        } catch (Exception e) {
            return "Http 500 - Internal Server Error. " + e.getMessage();
        }
    }

    public String checkTrackId(int trackId, Supplier<String> onSuccessAction) {
        try {
            Optional<Track> track = artistTrackService.getTrackById(trackId);
            if (track.isPresent()) {
                return onSuccessAction.get();
            } else {
                return "Http 404 - Not Found";
            }
        } catch (Exception e) {
            return "Http 500 - Internal Server Error. " + e.getMessage();
        }
    }
}
