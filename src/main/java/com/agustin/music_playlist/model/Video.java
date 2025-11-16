package src.main.java.com.agustin.music_playlist.model;

public class Video {
    private Long id;
    private String title;
    private String url;
    private boolean isLiked;
    private boolean isFavorite;


public Video() {
}

public Video(Long id, String title, String url) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.isLiked = false;
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
public boolean isLiked() {
    return isLiked;
}
public void setLiked(boolean liked) {
    isLiked = liked;
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

public String getEmbedUrl(String url) {
    String videoId = "";
    if (url.contains("youtube.com/watch?v=")) {
        int videoIdIndex = url.indexOf("v=") + 2;
        videoId = url.substring(videoIdIndex);
    }
    else if (url.contains("youtu.be/")) {
        int videoIdIndex = url.indexOf("youtu.be/") + 9;
        videoId = url.substring(videoIdIndex);
    } 
    return "https://www.youtube.com/embed/" + videoId;
}

}
    