# rickVideo
rickVideo uses Spring and React frameworks to create a video-sharing website.

## Technologies used
[Spring Framework](https://spring.io/) - 2.3.4    
[aws-java-sdk](https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk/1.11.879) - 1.11.879     
[apache-maven](http://maven.apache.org/download.cgi) - 3.6.3  
[react](https://github.com/facebook/create-react-app) - 16.13.1     
[axios](https://github.com/axios/axios) - 0.20.0      
[react-player](https://github.com/CookPete/react-player) - 2.6.2

## How to get it from git
```sh
$ git clone https://github.com/riccardopaltrinieri/rickVideo.git
```
For debugging and contributing you can open it in your favourite IDE (I used 
[IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) 
for the backend and [VisualStudio Code](https://code.visualstudio.com/Download)
for the React frontend).
    
## Usage
Fill the [aws file](src/main/resources/aws.properties) with the keys of your aws profile.       
Run the [main application](src/main/java/com/rickpalt/rickvideo/RickVideoApplication.java)
in the java IDE, or you can create the jar with maven and run it in the terminal with the command:
```sh
$ cd rickVideo/release
$ java -jar rickvideo-[last-version].jar
```
Then you can run the frontend with another terminal:
```sh
$ cd rickVideo/src/main/frontend
$ npm start
```


### License
This project is licensed under the terms of the MIT license.

