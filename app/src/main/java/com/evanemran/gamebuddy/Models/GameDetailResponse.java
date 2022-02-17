package com.evanemran.gamebuddy.Models;

import java.util.List;

public class GameDetailResponse {
    public int id;
    public String title;
    public String thumbnail;
    public String status;
    public String short_description;
    public String description;
    public String game_url;
    public String genre;
    public String platform;
    public String publisher;
    public String developer;
    public String release_date;
    public String freetogame_profile_url;
    public MinimumSystemRequirements minimum_system_requirements;
    public List<Screenshot> screenshots;

    public class MinimumSystemRequirements{
        public String os;
        public String processor;
        public String memory;
        public String graphics;
        public String storage;
    }

    public class Screenshot{
        public int id;
        public String image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getDescription() {
        return description;
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

    public MinimumSystemRequirements getMinimum_system_requirements() {
        return minimum_system_requirements;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }
}
