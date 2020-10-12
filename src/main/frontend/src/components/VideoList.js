import React from "react";
import { Grid } from "@material-ui/core";

import VideoItem from "./VideoItem";

export default ({ videos, onVideoSelect }) => {
    
    const listOfVideos = videos.map(video => (
    <VideoItem
      onVideoSelect={onVideoSelect}
      key={video.videoId}
      video={video}
    />
  ));

  return (
    <Grid container>
      {listOfVideos}
    </Grid>
  );
}