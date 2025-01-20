package com.pluralsight.controller;

import com.pluralsight.domain.Artist;
import com.pluralsight.domain.Track;
import com.pluralsight.enums.ArtistType;
import com.pluralsight.enums.Genre;
import com.pluralsight.service.ArtistTrackService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

// @Controller
// @RequestMapping("/api/v1/artist")
public class ArtistController {

    private final ArtistTrackService artistTrackService;

    public ArtistController(ArtistTrackService artistTrackService) {
        this.artistTrackService = artistTrackService;
    }

    //     @PostMapping(path = "/new")
    public String postRequestCreateArtist(/*@RequestBody*/ Artist artist) {
        if (artist == null) return "Http 404 Not Found";
        if (artist.getId() > 0) return "Http 400 Bad Request";
        try {
            artistTrackService.saveArtist(artist);
            System.out.println("Artist created successfully");
            return "Http 200 OK";
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

    //    @PutMapping(path = "/update/id/{artistId}")
    public String putRequestUpdateArtist(/*@PathVariable*/ int artistId, /*@RequestBody*/ Artist artist) {
        if (artist == null) return "Http 404 Not Found";
        if (artistId <= 0 || artist.getId() <= 0) return "Http 400 Bad Request";
        if (artistId != artist.getId()) return "Http 409 Conflict";
        try {
            Optional<Artist> optionalArtist = artistTrackService.saveArtist(artist);
            if (optionalArtist.isPresent()) {
                System.out.println("Artist updated successfully");
                return "Http 200 OK";

            } else {
                return "Http 404 Not Found";
            }
        }  catch (Exception e) {
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

    //    @DeleteMapping(path = "/id/{artistId}/delete")
    public String deleteRequestDeleteArtist(/*@PathVariable*/ int artistId) {
        if (artistId <= 0) return "Http 404 Not Found";
        return processGetArtistByIdRequest(artistId, () -> {
            artistTrackService.deleteArtistById(artistId);
            return "Http 200 OK";
        });
    }

    //    @GetMapping(path = "/find/artists/all")
    public String getRequestGetAllArtists() {
        try {
            List<Artist> allArtists = artistTrackService.getAllArtists();
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
            List<Artist> artistsByGenreList = artistTrackService.getByArtistGenre(genre);
            if (artistsByGenreList.isEmpty()) {
                System.out.println("No artists found");
                return "Http 404 Not Found";
            }
            artistsByGenreList.forEach(System.out::println);
            return "Http 200 OK";
        } catch (Exception e) {
            System.out.println();
            return "Http 500 Internal Server Error. " + e.getMessage();
        }
    }

    //    @GetMapping(path = "/find/all/name/{name}
    public String getRequestGetAllArtistsByName(/*@PathVariable*/ String name) {
        if (name == null || name.isBlank()) return "Http 404 Not Found";
        try {
            List<Artist> artistsByNameList = artistTrackService.getByName(name);
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
            List<Artist> artistsByNationalityList = artistTrackService.getByArtistNationality(nationality);
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
        if (yearFounded < 0) return "Http 404 Not Found";
        try {
            List<Artist> artistsByYearFoundedList = artistTrackService.getByArtistYearFounded(yearFounded);
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
            List<Artist> artistsByTrackList = artistTrackService.getByArtistTrackId(track.getId());
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
    public String getRequestGetAllArtistsByArtistType(/*@PathVariable*/ ArtistType artistType) {
        if (artistType == null) return "Http 404 Not Found";
        try {
            List<Artist> artistsByArtistTypeList = artistTrackService.getByArtistType(artistType);
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

    protected String processGetArtistByIdRequest(int artistId, Supplier<String> onSuccessAction) {
        try {
            Optional<Artist> optionalArtist = artistTrackService.getArtistById(artistId);
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