package youtube;

import youtube.component.YoutubeStreamingOutput;
import youtube.services.ReadFileService;

public class Main {
    public static void main(String[] args) {
//        String fileName = "youtube.txt";
//
//        ReadFileService readFileService = new ReadFileService();
//        readFileService.parseFile(fileName);
//
//        YoutubeStreamingOutput.getInstance().setVideoCacheMap();
////        YoutubeStreamingOutput.getInstance().setValues();
//
//        Double score = YoutubeStreamingOutput.getInstance().getScore();
//        System.out.println(String.valueOf(score));
//        YoutubeStreamingOutput.getInstance().validation();
        start:
        for(int i=0; i<5; i++){
            System.out.println("i" + i);
            for(int j=0;j<5;j++){
                if(i==j) continue start;
                System.out.println("j" + j);
            }
        }
    }
}
