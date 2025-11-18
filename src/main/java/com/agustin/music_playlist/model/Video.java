package com.agustin.music_playlist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Video {
    private Long id;
    private String title;
    private String url;
    private int likes;
    private boolean isFavorite;

    public Video() {
    }

    public Video(Long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.likes = 0;
        this.isFavorite = false;
    }

    public Long getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getUrl() {
        return url;
    }
    
    public int getLikes() {
        return likes;
    }
    
    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getEmbedUrl() {
        String videoId = "";
        if (this.url.contains("youtube.com/watch?v=")) {
            int videoIdIndex = this.url.indexOf("v=") + 2;
            videoId = this.url.substring(videoIdIndex);
        }
        else if (this.url.contains("youtu.be/")) {
            int videoIdIndex = this.url.indexOf("youtu.be/") + 9;
            videoId = this.url.substring(videoIdIndex);
        } 
        return "https://www.youtube.com/embed/" + videoId;
    }
}