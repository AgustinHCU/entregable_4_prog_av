package com.agustin.music_playlist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agustin.music_playlist.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class HelloController {
    
    @Autowired
    private PlaylistService service;

    @GetMapping("/")
    public String hello() {
        return "Hello World from Spring Boot!";
    }

    @GetMapping("/test")
    public String test() {
        service.addVideo("Test Video", "https://youtube.com/watch?v=dQw4w9WgXcQ");
        return "Videos: " + service.getAllVideos().size();
    }
}