package com.rickpalt.rickvideo.datastore;

import com.rickpalt.rickvideo.video.Video;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class VideoDataStore {

    private static final List<Video> VIDEO_LIST = new ArrayList<>();

    static {
        VIDEO_LIST.add(new Video(UUID.fromString("2aeb885d-8630-4880-b4a3-e83aaa8f6a80"), "janetjones"));
        VIDEO_LIST.add(new Video(UUID.fromString("9e54dfd9-2901-41c1-9c6d-799d21df0a84"), "antoniojunior"));
    }
    public List<Video> getVideoList(){
        return VIDEO_LIST;
    }
}
