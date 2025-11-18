package com.agustin.music_playlist.service;

import com.agustin.music_playlist.model.Video;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlaylistService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path storagePath;

    private List<Video> videos = new ArrayList<>();
    private Long nextId = 1L;

    // Default constructor – production
    public PlaylistService() {
        this.storagePath = Path.of("playlist_data.json");
        loadFromFile();
    }

    // Constructor for tests
    public PlaylistService(String storageFilePath) {
        this.storagePath = Path.of(storageFilePath);
        loadFromFile();
    }

    // ------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------

    public synchronized List<Video> getAllVideos() {
        return Collections.unmodifiableList(videos);
    }

    public synchronized void addVideo(String title, String url) {
        Video newVideo = new Video(nextId++, title, url);
        videos.add(newVideo);
        saveToFile();
    }

    public synchronized Video getVideoByTitle(String title) {
        return videos.stream()
                .filter(v -> v.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public synchronized void deleteVideo(Long id) {
        boolean removed = videos.removeIf(v -> v.getId().equals(id));
        if (removed) saveToFile();
    }

    public synchronized void likeVideo(Long id) {
        videos.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .ifPresent(v -> {
                    v.setLikes(v.getLikes() + 1);
                    saveToFile();
                });
    }

    public synchronized void toggleFavorite(Long id) {
        videos.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .ifPresent(v -> {
                    v.setFavorite(!v.isFavorite());
                    saveToFile();
                });
    }

    // ------------------------------------------------------------
    // Persistence
    // ------------------------------------------------------------

    private void loadFromFile() {
        try {
            File file = storagePath.toFile();

            if (!file.exists()) {
                // No data file yet
                videos = new ArrayList<>();
                nextId = 1L;
                return;
            }

            // If empty file → treat as empty list
            if (Files.size(storagePath) == 0L) {
                videos = new ArrayList<>();
                nextId = 1L;
                return;
            }

            byte[] json = Files.readAllBytes(storagePath);
            List<Video> list = objectMapper.readValue(json, new TypeReference<List<Video>>() {});
            if (list == null) list = new ArrayList<>();

            videos = new ArrayList<>(list);

            // Recalculate next ID
            nextId = videos.stream()
                    .mapToLong(v -> v.getId() == null ? 0L : v.getId())
                    .max()
                    .orElse(0L) + 1L;

        } catch (Exception e) {
            System.err.println("Failed to load playlist from " + storagePath + ": " + e.getMessage());
            videos = new ArrayList<>();
            nextId = 1L;
        }
    }

    private void saveToFile() {
        try {
            File parent = storagePath.toFile().getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            // Jackson creates the file itself – don't create it manually
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(storagePath.toFile(), videos);

        } catch (IOException e) {
            System.err.println("Failed to save playlist to " + storagePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
