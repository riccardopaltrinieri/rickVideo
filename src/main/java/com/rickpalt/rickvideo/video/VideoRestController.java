package com.rickpalt.rickvideo.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/", "/video", "/Home", "/home"})
@CrossOrigin("*")
public class VideoRestController {

    private final VideoService videoService;

    @Autowired
    public VideoRestController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<Video> getVideos() {
        return videoService.getVideos();
    }

    @PostMapping(
            path = "{videoName}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadVideo(@PathVariable("videoName") String videoName,
                                       @RequestParam("file") MultipartFile file) {
        videoService.uploadVideo(videoName, file);
    }

    @GetMapping("/video/{videoId}")
    public byte[] downloadVideo(@PathVariable("videoId") UUID videoId) {
        byte[] stream = videoService.downloadVideo(videoId);
        System.out.println(stream);
        return stream;
    }

    @GetMapping("/thumbnail/{videoId}")
    public byte[] getThumbnail(@PathVariable("videoId") UUID videoId) {
        return videoService.getThumbnail(videoId);
    }
}
