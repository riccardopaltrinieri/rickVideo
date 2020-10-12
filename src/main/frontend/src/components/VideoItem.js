import React from "react";
import { Grid, Paper, Typography } from "@material-ui/core";

export default ({ video, onVideoSelect }) => {

  const imgSrc = "http://localhost:8080/thumbnail/" + video.videoId;

  return (
    <Grid item xs={12} style={{ paddingTop: "15px" }} onClick={() => onVideoSelect(video)}>
        <img style={{ marginRight: "20px", maxWidth: "320px" }} alt="thumbnail" src={imgSrc}/>
      <Typography variant="subtitle1">
        <b>{video.videoName}</b>
      </Typography>
    </Grid>
  );
}