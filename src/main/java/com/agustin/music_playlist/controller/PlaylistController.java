package com.agustin.music_playlist.controller;
import com.agustin.music_playlist.service.PlaylistService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.agustin.music_playlist.model.Video;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlaylistController {
    
    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/")
    public String showPlaylist(Model model) {
        List<Video> videos = playlistService.getAllVideos();
        model.addAttribute("videos", videos);
        return "playlist";
    }
    @PostMapping("/add")
    public String addVideo(@RequestParam String title, @RequestParam String url) {
        playlistService.addVideo(title, url);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteVideo(@PathVariable Long id) {
        playlistService.deleteVideo(id);
        return "redirect:/";
    }

    @PostMapping("/like/{id}")
    public String likeVideo(@PathVariable Long id) {
        playlistService.likeVideo(id);
        return "redirect:/";
    }

    @PostMapping("/favorite/{id}")
    public String favoriteVideo(@PathVariable Long id) {
        playlistService.toggleFavorite(id);
        return "redirect:/";
    }

    @GetMapping("/favorites")
    public String showFavoriteNames(Model model) {
        List<String> names = playlistService.getFavoriteVideos()
                .stream()
                .map(Video::getTitle)
                .collect(Collectors.toList());
        model.addAttribute("favoriteNames", names);
        model.addAttribute("videos", playlistService.getAllVideos());
        return "playlist";
    }

    @GetMapping("/most-liked")
    public String showMostLiked(Model model) {
        Video most = playlistService.getMostLikedVideo();
        model.addAttribute("mostLiked", most);
        model.addAttribute("videos", playlistService.getAllVideos());
        return "playlist";
    }

    // ----- CODE SMELL INTENCIONAL (para la demo) -----
    // Este método contiene lógica duplicada y mezcla responsabilidades:
    // valida parámetros, modifica el modelo y realiza acciones sobre el servicio.
    // Es intencionalmente verboso e innecesariamente complejo para mostrar
    // un "code smell" que se corregirá durante la demo mediante refactor.
    public String processAction(String action, Long id, Model model) {
        // Validación pobre (duplicada en varios lugares)
        if (action == null || action.isEmpty()) {
            model.addAttribute("error", "No action provided");
            return "playlist";
        }

        // Lógica duplicada: manejo de 'like' y 'favorite' con código similar
        if (action.equals("like")) {
            // comprobar id
            if (id == null || id <= 0) {
                model.addAttribute("error", "Invalid id for like");
                return "playlist";
            }
            // realizar la acción
            playlistService.likeVideo(id);
            // actualizar modelo
            model.addAttribute("message", "Liked video " + id);
            List<Video> videos = playlistService.getAllVideos();
            model.addAttribute("videos", videos);
            return "playlist";
        }

        if (action.equals("favorite")) {
            if (id == null || id <= 0) {
                model.addAttribute("error", "Invalid id for favorite");
                return "playlist";
            }
            playlistService.toggleFavorite(id);
            model.addAttribute("message", "Toggled favorite " + id);
            List<Video> videos = playlistService.getAllVideos();
            model.addAttribute("videos", videos);
            return "playlist";
        }

        model.addAttribute("error", "Unknown action");
        return "playlist";
    }


}
