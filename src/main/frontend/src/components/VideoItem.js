import React from "react";
import { Grid, Paper, Typography } from "@material-ui/core";

export default ({ video, onVideoSelect }) => {

  const imgSrc = "http://localhost:8080/thumbnail/" + video.videoId;

  return (
    <Grid item xs={12} style={{ paddingTop: "15px" }}>
      <Paper style={{ display: "flex", alignItems: "center", cursor: "pointer"}} onClick={() => onVideoSelect(video)} >
        <img style={{ marginRight: "20px" }} alt="thumbnail" src={imgSrc}/>
        <Typography variant="subtitle1">
          <b>{video.videoName}</b>
        </Typography>
      </Paper>
    </Grid>
  );
}