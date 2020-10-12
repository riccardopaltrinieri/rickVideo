import React, { useState, useEffect } from 'react'
import { Grid } from '@material-ui/core'
import { NavBar, VideoList, VideoDetail } from "./components";
import axios from "axios"

export default () => {
  const [videos, setVideos] = useState([]);
  const [selectedVideo, setSelectedVideo] = useState(null);

  useEffect(() => {
    getVideos();
  }, [])

  return(
    <Grid container>
      <Grid container spacing={10}>
        <Grid item xs={12}>
          <NavBar onClick={getVideos}/>
        </Grid>
        <Grid justify="center" container spacing={10}>
          <Grid item xs={6}>
            <VideoDetail video={selectedVideo} />
          </Grid>
          <Grid item xs={3}>
            <VideoList videos={videos} onVideoSelect={setSelectedVideo} />
          </Grid>
        </Grid>
      </Grid>  
    </Grid>
  );

  async function getVideos() {
    const response = await axios.get("http://localhost:8080/video");
    
    console.log(response);

    setVideos(response.data);
    setSelectedVideo(response.data[0]);
  }
}
