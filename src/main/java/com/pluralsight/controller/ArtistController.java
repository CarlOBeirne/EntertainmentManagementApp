package com.pluralsight.controller;

import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;
import com.pluralsight.service.ArtistDataService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

// @Controller
// @RequestMapping("/api/v1/artist")
public class ArtistController {

    private final ArtistDataService artistDataService = new ArtistDataService();

    //     @PostMapping(path = "/new")
    public String postRequestCreateArtist(/*@RequestBody*/ Artist artist) {
        if (artist == null) return "Http 404 Not Found";
        if (artist.getId() > 0) return "Http 400 Bad Request";
        try {
            artistDataService.saveArtist(artist);
            System.out.println("Artist created successfully");
            return "Http 200 OK";
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

    //    @PutMapping(path = "/update/id/{artistId}")
    public String putRequestUpdateArtist(/*@PathVariable*/ int artistId, /*@RequestBody*/ Artist artist) {
        if (artist == null) return "Http 404 Not Found";
        if (artistId <= 0 || artist.getId() <= 0) return "Http 400 Bad Request";
        if (artistId != artist.getId()) return "Http 409 Conflict";
        try {
            artistDataService.saveArtist(artist);
            System.out.println("Artist updated successfully");
            return "Http 200 OK";
        }  catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

//    @DeleteMapping(path = "/id/{artistId}/delete")
    public String deleteRequestDeleteArtist(/*@PathVariable*/ int artistId) {
        if (artistId <= 0) return "Http 404 Not Found";
        return processGetArtistByIdRequest(artistId, () -> {
            artistDataService.deleteArtistById(artistId);
            return "Http 200 OK";
        });
    }

//    @GetMapping(path = "/find/artists/all")
    public String getRequestGetAllArtists() {
        try {
            List<Artist> allArtists = artistDataService.getAllArtists();
            if (allArtists.isEmpty()) {
                System.out.println("No artists found");
                return "Http 404 Not Found";
            }
            // Demo that there is actually artists in the list
            System.out.println(allArtists.getFirst());
            return "Http 200 OK";
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

//    @GetMapping(path = "/find/id/{artistId}")
    public String getRequestGetArtistById(int artistId) {
        return processGetArtistByIdRequest(artistId, () -> "Http 200 OK");
    }

    //    @GetMapping(path = "/find/all/genre/{genre}
    public String getRequestGetAllArtistsByGenre(/*@PathVariable*/ Genre genre) {
        if (genre == null) return "Http 404 Not Found";
        try {
            List<Artist> artistsByGenreList = artistDataService.getByGenre(genre);
            if (artistsByGenreList.isEmpty()) {
                System.out.println("No artists found");
                return "Http 404 Not Found";
            }
            artistsByGenreList.forEach(System.out::println);
            return "Http 200 OK";
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

//    @GetMapping(path = "/find/all/name/{name}
    public String getRequestGetAllArtistsByName(/*@PathVariable*/ String name) {
        if (name == null || name.isBlank()) return "Http 404 Not Found";
        try {
            List<Artist> artistsByNameList = artistDataService.getByName(name);
            if (artistsByNameList.isEmpty()) {
                System.out.println("No artists found");
                return "Http 404 Not Found";
            }
            artistsByNameList.forEach(System.out::println);
            return "Http 200 OK";
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

//    @GetMapping(path = "/find/all/nationality/{nationality}
    public String getRequestGetAllArtistsByNationality(/*@PathVariable*/ String nationality) {
        if (nationality == null || nationality.isBlank()) return "Http 404 Not Found";
        try {
            List<Artist> artistsByNationalityList = artistDataService.getByNationality(nationality);
            if (artistsByNationalityList.isEmpty()) {
                System.out.println("No artists found");
                return "Http 404 Not Found";
            }
            artistsByNationalityList.forEach(System.out::println);
            return "Http 200 OK";
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

//    @GetMapping(path = "/find/all/year/{yearFounded}
    public String getRequestGetAllArtistsByYearFounded(/*@PathVariable*/ int yearFounded) {
//        if (yearFounded < 0) return "Http 404 Not Found";
        try {
            List<Artist> artistsByYearFoundedList = artistDataService.getByYearFounded(yearFounded);
            if (artistsByYearFoundedList.isEmpty()) {
                System.out.println("No artists found");
                return "Http 404 Not Found";
            }
            artistsByYearFoundedList.forEach(System.out::println);
            return "Http 200 OK";
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

//    @GetMapping(path = "/find/all/track/{track}
    public String getRequestGetAllArtistsByArtistTrack(/*@PathVariable*/ Track track) {
        if (track == null) return "Http 404 Not Found";
        try {
            List<Artist> artistsByTrackList = artistDataService.getByTrack(track);
            if (artistsByTrackList.isEmpty()) {
                System.out.println("No artists found");
                return "Http 404 Not Found";
            }
            artistsByTrackList.forEach(System.out::println);
            return "Http 200 OK";
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

//    @GetMapping(path = "/find/all/type/{artistType}
    public String getRequestGetAllArtistsByArtistTrack(/*@PathVariable*/ ArtistType artistType) {
        if (artistType == null) return "Http 404 Not Found";
        try {
            List<Artist> artistsByArtistTypeList = artistDataService.getByArtistType(artistType);
            if (artistsByArtistTypeList.isEmpty()) {
                System.out.println("No artists found");
                return "Http 404 Not Found";
            }
            artistsByArtistTypeList.forEach(System.out::println);
            return "Http 200 OK";
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

    private String processGetArtistByIdRequest(int artistId, Supplier<String> onSuccessAction) {
        try {
            Optional<Artist> optionalArtist = artistDataService.getArtistById(artistId);
            if (optionalArtist.isPresent()) {
                return onSuccessAction.get();
            } else {
                return "Http 404 Not Found";
            }
        } catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }
}