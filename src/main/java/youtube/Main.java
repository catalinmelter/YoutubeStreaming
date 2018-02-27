package youtube;

import youtube.component.YoutubeStreaming;
import youtube.component.YoutubeStreamingOutput;
import youtube.services.ReadFileService;

public class Main {
    public static void main(String[] args) {
        String fileName = "file.txt";

        ReadFileService readFileService = new ReadFileService();
        readFileService.parseFile(fileName);

        YoutubeStreaming youtubeStreaming = YoutubeStreaming.getInstance();
        YoutubeStreamingOutput.getInstance().setVideoCacheMap();
//        YoutubeStreamingOutput.getInstance().setValues();

        YoutubeStreamingOutput youtubeStreamingOutput = YoutubeStreamingOutput.getInstance();
        Double score = YoutubeStreamingOutput.getInstance().getScore();
        System.out.println(String.valueOf(score));
        YoutubeStreamingOutput.getInstance().validation();
    }
}
