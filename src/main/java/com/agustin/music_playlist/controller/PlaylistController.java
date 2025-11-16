package com.agustin.music_playlist.controller;
import com.agustin.music_playlist.service.PlaylistService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.agustin.music_playlist.model.Video;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlaylistController {
    
    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/")
    public String showPlaylist(Model model) {
        List<Video> videos = playlistService.getAllVideos();
        model.addAttribute("video", videos);
        return "playlist";
    }
    @PostMapping("/add")
    public String addVideo(@RequestParam String title, @RequestParam String url) {
        playlistService.addVideo(title, url);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteVideo(@RequestParam Long id) {
        playlistService.deleteVideo(id);
        return "redirect:/";
    }

    @PostMapping("/like/{id}")
    public String likeVideo(@RequestParam Long id) {
        playlistService.likeVideo(id);
        return "redirect:/";
    }

    @PostMapping("/toggleFavorite/{id}")
    public String toggleFavorite(@RequestParam Long id) {
        playlistService.toggleFavorite(id);
        return "redirect:/";
    }


}
