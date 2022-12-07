package com.tg.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\tina.gosaric\\Desktop\\Learning\\0 JAVA\\Projects\\TestDB\\" + DB_NAME;
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";

// before(with StringBuilder): SELECT name, album, track FROM artist_list WHERE title = "Go Your Own Way" or 1=1 or ""
//                        now: SELECT name, album, track FROM artist_list WHERE title = "Go Your Own Way or 1=1 or ""

/*** PERFORM THESE ACTIONS TO PREVENT AN INJECTION ATTACK ***/

/*** 1. Declare a constant for the SQL statement that contains the placeholders ***/
    public static final String QUERY_VIEW_SONG_INFO_PREP = "SELECT " + COLUMN_ARTIST_NAME + ", " +
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW +
            " WHERE " + COLUMN_SONG_TITLE + " = ?" + " AND " + COLUMN_SONG_ALBUM + " =?";

    private Connection conn;

    private PreparedStatement querySongInfoView;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

/*** 2. Create a PreparedStatement instance ***/
            querySongInfoView = conn.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (querySongInfoView != null) {
                querySongInfoView.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public List<SongArtist> querySongInfoView(String myTitle, String myAlbum) {
        try {

/*** 3. We call the appropriate setter methods to set the placeholders to the values we want to use in the statement ***/
            querySongInfoView.setString(1, myTitle);
            querySongInfoView.setString(2, myAlbum);

/*** 4. We run the statement ***/
            ResultSet results = querySongInfoView.executeQuery();

/*** 5. We process the results ***/
            List<SongArtist> songArtists = new ArrayList<>();
            while (results.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(results.getString(1));
                songArtist.setAlbumName(results.getString(2));
                songArtist.setTrack(results.getInt(3));
                songArtists.add(songArtist);
            }
            return songArtists;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
}


