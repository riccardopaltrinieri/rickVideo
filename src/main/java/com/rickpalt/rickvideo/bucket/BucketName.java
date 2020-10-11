package com.rickpalt.rickvideo.bucket;

public enum BucketName {

    BUCKET_LINK("rickvideo-aws-storage");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
