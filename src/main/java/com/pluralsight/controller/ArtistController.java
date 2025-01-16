package com.pluralsight.controller;

import com.pluralsight.domain.Artist;

// @Controller
// @RequestMapping("/api/v1/artist")
public class ArtistController {

    private final ArtistDataService artistDataService = new ArtistDataService();

    //     @PostMapping(path = "/new")
    public String postRequestSaveArtist(/*@RequestBody*/ Artist artist) {
        if (artist == null) throw new NullPointerException("Artist cannot be empty");
        try {
            artistDataService.save(artist);
            System.out.println("Artist created successfully");
            return "Http 200 OK";
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
            throw npe;
        } catch (Exception e) {
            throw new RuntimeException("Error saving artist", e);
        }
    }

    //    @PutMapping(path = "/update/{artistId}")
    public String putRequestSaveArtist(/*@PathVariable*/ int artistId, /*@RequestBody*/ Artist artist) {
        if (artist == null) {
            throw new NullPointerException("Artist cannot be empty");
        } else if (artistId < 0) {
            throw new IllegalArgumentException("Artist id cannot be negative");
        }
        try {
            if (artistId != artist.getId()) {
                throw new IllegalArgumentException("Artist id mismatch");
            }
            artistDataService.save(artist);
            System.out.println("Artist updated successfully");
            return "Http 200 OK";
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
            throw npe;
        } catch (Exception e) {
            throw new RuntimeException("Unknown error updating artist", e);
        }
    }
}