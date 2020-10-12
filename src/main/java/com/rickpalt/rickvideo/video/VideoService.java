package com.rickpalt.rickvideo.video;

import com.rickpalt.rickvideo.bucket.BucketName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class VideoService {

    private final VideoDataAccessService videoDataAccessService;

    @Autowired
    public VideoService(VideoDataAccessService videoDataAccessService) {
        this.videoDataAccessService = videoDataAccessService;
    }

    public List<Video> getVideos() {
        return videoDataAccessService.getVideoList();
    }

    public void uploadVideo(String videoName, MultipartFile file) {
        // 1. Check if video is not empty
        isFileEmpty(file);
        // 2. If file is a video
        isVideo(file);
        // 3. Create the video in the database
        Video video = new Video(UUID.randomUUID(), videoName);
        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.BUCKET_LINK.getBucketName(), video.getVideoId());
        String filename = file.getOriginalFilename();

        try {
            videoDataAccessService.save(path, filename, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isVideo(MultipartFile file) {
        if (!Arrays.asList("video/mpeg", "video/ogg", "video/mp4", "video/3gpp", "application/mp4",
                "application/x-mpegurl", "video/webm")
                .contains(file.getContentType()))
            throw new IllegalStateException("File must be a video [" + file.getContentType() + "]");
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()) throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
    }

    public byte[] downloadVideo(UUID videoId) {
        Video video = getVideoOrThrow(videoId);
        String key = String.format("%s/%s", videoId, video.getVideoName());

        return videoDataAccessService.download(BucketName.BUCKET_LINK.getBucketName(), key);
    }

    private Video getVideoOrThrow(UUID videoId) {
        return videoDataAccessService
                .getVideoList()
                .stream()
                .filter(video -> video.getVideoId().equals(videoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Video %s not found", videoId)));
    }

    public byte[] getThumbnail(UUID videoId) {
        Video video = getVideoOrThrow(videoId);
        String key = String.format("%s/%s.jpg", videoId, video.getVideoName());

        return videoDataAccessService.download(BucketName.BUCKET_LINK.getBucketName(), key);
    }
}
