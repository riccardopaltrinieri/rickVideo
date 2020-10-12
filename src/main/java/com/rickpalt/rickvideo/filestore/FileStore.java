package com.rickpalt.rickvideo.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.rickpalt.rickvideo.bucket.BucketName;
import com.rickpalt.rickvideo.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class FileStore {

    private final AmazonS3 s3;

    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path,
                     String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {

        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty())
                map.forEach(metadata::addUserMetadata);
        });

        try{
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file to se3", e);
        }
    }

    public byte[] download(String bucketName, String key) {
        try {
            S3Object object = s3.getObject(bucketName, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file to s3", e);
        }
    }

    public List<Video> getVideoList() {
        List<Video> videos = new LinkedList<Video>();
        try {
            ListObjectsV2Request req = new ListObjectsV2Request()
                    .withBucketName(BucketName.BUCKET_LINK.getBucketName())
                    .withMaxKeys(10);
            ListObjectsV2Result result;

            do {
                result = s3.listObjectsV2(req);

                for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                    if(!objectSummary.getKey().endsWith(".jpg")) {
                        System.out.println(objectSummary);
                        String[] split = objectSummary.getKey().split("/");
                        videos.add(new Video(UUID.fromString(split[0]), split[1]));
                    }
                }
                // If there are more than maxKeys keys in the bucket, get a continuation token
                // and list the next objects.
                String token = result.getNextContinuationToken();
                req.setContinuationToken(token);
            } while (result.isTruncated());
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return videos;
    }
}
