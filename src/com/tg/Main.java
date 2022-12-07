package com.tg;

import com.tg.model.Artist;
import com.tg.model.Datasource;
import com.tg.model.SongArtist;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if (!datasource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        List<SongArtist> songArtists = datasource.querySongInfoView("Go Your Own Way", "The Dance");

        if (songArtists.isEmpty()) {
            System.out.println("Couldn't find the artist for the song");
            return;
        }

        for (SongArtist artist : songArtists) {
            System.out.println("Artist name = " + artist.getArtistName() +
                    ", Album name = " + artist.getAlbumName() + ", Track number = " + artist.getTrack());
        }

        datasource.close();
    }
}