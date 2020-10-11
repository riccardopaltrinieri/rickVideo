package com.rickpalt.rickvideo.video;

import com.rickpalt.rickvideo.datastore.VideoDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoDataAccessService {

    private final VideoDataStore videoDataStore;

    @Autowired
    public VideoDataAccessService(VideoDataStore videoDataStore) {
        this.videoDataStore = videoDataStore;
    }

    public List<Video> getVideoList() {
        return videoDataStore.getVideoList();
    }
}
