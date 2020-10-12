import React from 'react'
import { Paper, Typography} from '@material-ui/core'
import ReactPlayer from 'react-player/lazy'

const VideoDetail = ({ video }) => {

    if(!video) return <div></div> 

    const videoSrc = `http://localhost:8080/video/${video.videoId}`; 

    return (
        <React.Fragment>
        <Paper elevation={6} style={{heigth:'70%'}} id="video-container">
            <ReactPlayer url={videoSrc} controls={true} width='100%' height='100%'/>
        </Paper>
        <Paper elevation={6} style={{padding:'15px'}}>
            <Typography variant="h4">
                {video.videoName}
            </Typography>
            {/* <Typography variant="subtitle2">{video.snippet.description}</Typography>*/}
        </Paper>
        </React.Fragment>
    )
}

export default VideoDetail;