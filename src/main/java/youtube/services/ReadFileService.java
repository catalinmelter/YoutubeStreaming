package youtube.services;

import youtube.component.YoutubeStreaming;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFileService {
    private Integer videosLength;
    private Integer endpointsLength;
    private Integer requestsDesLength;
    private Integer cachesLength;

    public void parseFile(String fileName) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            // First Line
            line = br.readLine();
            firstLine(line);

            // Second Line
            line = br.readLine();
            YoutubeStreaming.getInstance().addVideo(line);

            // Endpoints Lines
            for (int i=0; i<this.endpointsLength; i++){
                line = br.readLine();
                String[] splited = line.split("\\s+");
                Integer datacenterLatency = Integer.parseInt(splited[0]);
                Integer cacheLinesLength = Integer.parseInt(splited[1]);
                List<String> lines = new ArrayList<>();
                for (int j=0; j<cacheLinesLength; j++){
                    line = br.readLine();
                    lines.add(line);
                }
                YoutubeStreaming.getInstance().addEndpoint(i, datacenterLatency, lines);
            }

            // Requests
            List<String> lines = new ArrayList<>();
            while((line = br.readLine()) != null){
                lines.add(line);
            }
            YoutubeStreaming.getInstance().setRequestDescriptionList(lines);

            // Validation
            System.out.println("Validation: " + validation());

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void firstLine(String line){
        String[] splited = line.split("\\s+");
        this.videosLength = Integer.parseInt(splited[0]);
        this.endpointsLength = Integer.parseInt(splited[1]);
        this.requestsDesLength = Integer.parseInt(splited[2]);
        this.cachesLength = Integer.parseInt(splited[3]);

        YoutubeStreaming.getInstance().setCaches(this.cachesLength, Integer.parseInt(splited[4]));
    }

    private boolean validation(){
        return (YoutubeStreaming.getInstance().getCaches().size() == this.cachesLength &&
                YoutubeStreaming.getInstance().getEndpoints().size() == this.endpointsLength &&
                YoutubeStreaming.getInstance().getRequestDescriptionList().size() == this.requestsDesLength &&
                YoutubeStreaming.getInstance().getVideos().size() == this.videosLength);
    }

}
