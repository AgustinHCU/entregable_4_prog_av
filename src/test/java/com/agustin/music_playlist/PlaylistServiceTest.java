package com.agustin.music_playlist;

import com.agustin.music_playlist.service.PlaylistService;
import com.agustin.music_playlist.model.Video;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistServiceTest {

    @Test
    void testAddAndPersist() throws Exception {
        Path tempDir = Files.createTempDirectory("playlist-test-dir");
        Path filePath = tempDir.resolve("playlist.json");
        String path = filePath.toAbsolutePath().toString();

        PlaylistService service = new PlaylistService(path);
        service.addVideo("Song A", "https://youtu.be/abc123");
        service.addVideo("Song B", "https://youtu.be/def456");

        List<Video> videos = service.getAllVideos();
        assertEquals(2, videos.size(), "Debe haber 2 videos luego de agregarlos");

        PlaylistService service2 = new PlaylistService(path);
        List<Video> videos2 = service2.getAllVideos();

        assertEquals(2, videos2.size(), "La persistencia debe conservar los 2 videos");
        assertTrue(videos2.stream().anyMatch(v -> v.getTitle().equals("Song A")),
                "El video 'Song A' debe existir después de recargar la lista");

        Files.deleteIfExists(filePath);
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testLikeAndFavorite() throws Exception {
        Path tempDir = Files.createTempDirectory("playlist-test-dir");
        Path filePath = tempDir.resolve("playlist.json");
        String path = filePath.toAbsolutePath().toString();

        PlaylistService service = new PlaylistService(path);
        service.addVideo("Track", "https://youtu.be/trackid");
        List<Video> videos = service.getAllVideos();
        assertEquals(1, videos.size(), "Debe haber 1 video");

        Long id = videos.get(0).getId();

        service.likeVideo(id);
        List<Video> afterLike = service.getAllVideos();
        assertEquals(1, afterLike.get(0).getLikes(),
                "El contador de likes debe aumentar a 1");

        service.toggleFavorite(id);
        assertTrue(service.getAllVideos().get(0).isFavorite(),
                "El video debe marcarse como favorito");

        Files.deleteIfExists(filePath);
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testDelete() throws Exception {
        Path tempDir = Files.createTempDirectory("playlist-test-dir");
        Path filePath = tempDir.resolve("playlist.json");
        String path = filePath.toAbsolutePath().toString();

        PlaylistService service = new PlaylistService(path);
        service.addVideo("One", "u1");
        service.addVideo("Two", "u2");

        List<Video> videos = service.getAllVideos();
        assertEquals(2, videos.size(), "Debe haber 2 videos");

        Long idToDelete = videos.get(0).getId();

        service.deleteVideo(idToDelete);
        assertEquals(1, service.getAllVideos().size(),
                "Después de borrar debe quedar solo 1 video");

        Files.deleteIfExists(filePath);
        Files.deleteIfExists(tempDir);
    }
}

