package com.rickpalt.rickvideo.bucket;

public enum BucketName {

    PROFILE_IMAGE("rickvideo-aws-storage");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
