package com.pluralsight.utils;

import com.pluralsight.domain.Artist;

import java.util.List;

public class ArtistHelper {
    public static String getArtistNameFromList(List<Artist> artists) {
        if (artists == null || artists.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Artist artist : artists) {
            sb.append(artist.getName()).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();

    }
}
