package com.rickpalt.rickvideo.video;


import java.util.Objects;
import java.util.UUID;

public class Video {

    private final UUID videoId;
    private final String videoName;

    public Video(UUID videoId, String videoName) {
        this.videoId = videoId;
        this.videoName = videoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video that = (Video) o;
        return Objects.equals(videoId, that.videoId) &&
                Objects.equals(videoName, that.videoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, videoName);
    }

    public UUID getVideoId() {
        return videoId;
    }

    public String getVideoName() {
        return videoName;
    }

}
