package com.rickpalt.rickvideo.video;


import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Video {

    private final UUID videoId;
    private final String videoName;
    private String videoLink; // aws S3 key

    public Video(UUID videoId, String videoName) {
        this.videoId = videoId;
        this.videoName = videoName;
        this.videoLink = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video that = (Video) o;
        return Objects.equals(videoId, that.videoId) &&
                Objects.equals(videoName, that.videoName) &&
                Objects.equals(videoLink, that.videoLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, videoLink);
    }

    public UUID getVideoId() {
        return videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public Optional<String> getVideoLink() {
        return Optional.ofNullable(videoLink);
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
