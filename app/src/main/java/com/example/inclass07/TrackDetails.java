package com.example.inclass07;

import java.io.Serializable;

public class TrackDetails implements Serializable {

    String trackname;
    String album;
    String artist;
    String date;
    String url;

    public String getTrackname() {
        return trackname;
    }

    public void setTrackname(String trackname) {
        this.trackname = trackname;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TrackDetails() {
    }

    public TrackDetails(String trackname, String album, String artist, String date) {
        this.trackname = trackname;
        this.album = album;
        this.artist = artist;
        this.date = date;
    }

    @Override
    public String toString() {
        return "TrackDetails{" +
                "trackname='" + trackname + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
