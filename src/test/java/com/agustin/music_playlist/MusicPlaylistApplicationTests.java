package com.agustin.music_playlist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PlaylistApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true);
    }
    
    @Test
    void simpleTest() {
        String message = "Hello World";
        assertTrue(message.contains("Hello"));
    }
}