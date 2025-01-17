package com.pluralsight.utils;

import com.pluralsight.domain.Track;

import java.util.List;

public class TrackHelper {
    public static String getTrackTitleFromList(List<Track> tracks) {
        if (tracks == null || tracks.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Track track : tracks) {
            sb.append(track.getTitle()).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();
    }
}
