package com.rickpalt.rickvideo.video;

import com.rickpalt.rickvideo.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class VideoDataAccessService {

    private final FileStore fileStore;

    @Autowired
    public VideoDataAccessService(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    public List<Video> getVideoList() {
        return fileStore.getVideoList();
    }

    public byte[] download(String bucketName, String key) {
        return fileStore.download(bucketName, key);
    }

    public void save(String path, String filename, Optional<Map<String, String>> metadata, InputStream inputStream) {
        fileStore.save(path, filename, metadata, inputStream);
    }
}
