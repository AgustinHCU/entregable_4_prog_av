package com.agustin.music_playlist;

import com.agustin.music_playlist.controller.PlaylistController;
import com.agustin.music_playlist.model.Video;
import com.agustin.music_playlist.service.PlaylistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaylistController.class)
public class PlaylistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private PlaylistService playlistService;

    @Test
    void favoritesEndpointProvidesFavoriteNames() throws Exception {
        List<Video> favs = List.of(
                new Video(1L, "Fav1", "https://youtu.be/vid1"),
                new Video(2L, "Fav2", "https://youtu.be/vid2")
        );

        when(playlistService.getFavoriteVideos()).thenReturn(favs);
        when(playlistService.getAllVideos()).thenReturn(favs);

        mockMvc.perform(get("/favorites"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("favoriteNames"))
                .andExpect(model().attribute("favoriteNames", hasItem("Fav1")));
    }

    @Test
    void mostLikedEndpointProvidesMostLikedVideo() throws Exception {
        Video a = new Video(1L, "A", "https://youtu.be/a");
        Video b = new Video(2L, "B", "https://youtu.be/b");
        b.setLikes(3);
        a.setLikes(1);

        when(playlistService.getMostLikedVideo()).thenReturn(b);
        when(playlistService.getAllVideos()).thenReturn(List.of(a, b));

        mockMvc.perform(get("/most-liked"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("mostLiked"))
                .andExpect(model().attribute("mostLiked", hasProperty("title", is("B"))));
    }
}
