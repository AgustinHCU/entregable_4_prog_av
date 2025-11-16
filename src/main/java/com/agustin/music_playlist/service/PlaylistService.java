package com.agustin.music_playlist.service;

import com.agustin.music_playlist.model.Video;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {
    
    private List<Video> videos = new ArrayList<>();
    private Long nextId = 1L;

    public List<Video> getAllVideos() {
        System.out.println("Fetching all videos. Total videos: " + videos.size());
        return videos;
    }
// add video
// get video by title
// update video title
// delete video
// toggle favorite
// toggle like
// 
    public void addVideo(String title, String url) {
        System.out.println("Current number of videos: " + videos.size());
        System.out.println("Adding video: " + title + " with URL: " + url);
        Video newVideo = new Video(nextId++, title, url);
        System.out.println("Created video with ID: " + newVideo.getId());
        videos.add(newVideo);
        System.out.println("Video added. Total videos: " + videos.size());
    }

    public Video getVideoByTitle(String title) {
        return videos.stream()
                .filter(video -> video.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public void deleteVideo(Long id) {
        videos.removeIf(video -> video.getId().equals(id));
    }

    public void likeVideo(Long id) {
        videos.stream()
                .filter(video -> video.getId().equals(id))
                .findFirst()
                .ifPresent(video -> video.setLikes(video.getLikes() + 1));
    }

    public void toggleFavorite(Long id) {
        videos.stream()
                .filter(video -> video.getId().equals(id))
                .findFirst()
                .ifPresent(video -> video.setFavorite(!video.isFavorite()));
    }

}
