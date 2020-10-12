import React, {useCallback} from 'react'
import {useDropzone} from 'react-dropzone'
import axios from "axios"

const Dropzone = () => {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);

    const formData = new FormData();
    formData.append("file", file);

    axios.post(
        'http://localhost:8080/' + file.name + '/upload',   // url
        formData,                                           // file param
        { 
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }).then(() => {
            console.log("file uploaded successfuly");
        }).catch(err => {
            console.log(err);
        });

  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()} style={{border: '1px solid #000', padding: '40px'}}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the files here ...</p> :
          <p>Drag 'n' drop the video here, or click to select the file</p>
      }
    </div>
  )
}

export default Dropzone;