package com.evanemran.gamebuddy.Models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "games")
public class GameListObject implements Serializable {
    @PrimaryKey()
    public int id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "thumbnail")
    public String thumbnail;
    @ColumnInfo(name = "short_description")
    public String short_description;
    @ColumnInfo(name = "game_url")
    public String game_url;
    @ColumnInfo(name = "genre")
    public String genre;
    @ColumnInfo(name = "platform")
    public String platform;
    @ColumnInfo(name = "publisher")
    public String publisher;
    @ColumnInfo(name = "developer")
    public String developer;
    @ColumnInfo(name = "release_date")
    public String release_date;
    @ColumnInfo(name = "freetogame_profile_url")
    public String freetogame_profile_url;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getGame_url() {
        return game_url;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getFreetogame_profile_url() {
        return freetogame_profile_url;
    }
}
