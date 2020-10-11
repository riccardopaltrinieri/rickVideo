package com.rickpalt.rickvideo.video;

import com.rickpalt.rickvideo.bucket.BucketName;
import com.rickpalt.rickvideo.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class VideoService {

    private final VideoDataAccessService videoDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public VideoService(VideoDataAccessService videoDataAccessService, FileStore fileStore) {
        this.videoDataAccessService = videoDataAccessService;
        this.fileStore = fileStore;
    }

    public List<Video> getVideos() {
        return videoDataAccessService.getVideoList();
    }

    public void uploadVideo(String videoName, MultipartFile file) {
        // 1. Check if video is not empty
        isFileEmpty(file);
        // 2. If file is a video
        isImage(file);
        // 3. Create the video in the database
        Video video = new Video(UUID.randomUUID(), videoName);
        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.BUCKET_LINK.getBucketName(), video.getVideoId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), video.getVideoId());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            video.setVideoLink(filename);
            videoDataAccessService.getVideoList().add(video);
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

    private void isImage(MultipartFile file) {
        if (!Arrays.asList("video/mpeg", "video/x-mpeg", "video/mp4").contains(file.getContentType()))
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()) throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
    }

    public byte[] downloadVideo(UUID videoId) {
        Video video = getVideoOrThrow(videoId);
        String path = BucketName.BUCKET_LINK.getBucketName() + videoId;

        return video.getVideoLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }

    private Video getVideoOrThrow(UUID videoId) {
        return videoDataAccessService
                .getVideoList()
                .stream()
                .filter(video -> video.getVideoId().equals(videoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Video %s not found", videoId)));
    }
}
