package youtube.component;

import youtube.models.Cache;
import youtube.models.Endpoint;
import youtube.models.RequestDescription;
import youtube.models.Video;

import java.util.ArrayList;
import java.util.List;

public class YoutubeStreaming {
    private List<Video> videos = new ArrayList<>();
    private List<Endpoint> endpoints = new ArrayList<>();
    private List<Cache> caches = new ArrayList<>();
    private List<RequestDescription> requestDescriptionList = new ArrayList<>();

    private YoutubeStreaming(){}

    private static class SingletonHolder {
        static final YoutubeStreaming INSTANCE = new YoutubeStreaming();
    }

    public static YoutubeStreaming getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void addVideo(String line){
        String[] splited = line.split("\\s+");
        for (int i=0; i<splited.length; i++) {
            Integer number = Integer.parseInt(splited[i]);
            videos.add(new Video(i, number));
        }
    }

    public void setCaches(Integer cachesLength, Integer mb){
        for(int i = 0; i < cachesLength; i++){
            caches.add(new Cache(i, mb));
        }
    }

    public void addEndpoint(Integer datacenterLatency, List<String> lines){
        Endpoint endpoint = new Endpoint(datacenterLatency);
        for(String line: lines){
            String[] splitedIn = line.split("\\s+");
            endpoint.addCacheLatency(caches.get(Integer.parseInt(splitedIn[0])), Integer.parseInt(splitedIn[1]));
        }
        endpoint.sortByValue();
        endpoints.add(endpoint);
    }

    public void setRequestDescriptionList(List<String> lines) {
        for (String line: lines){
            String[] splited = line.split("\\s+");
            requestDescriptionList.add(new RequestDescription(
                                        Integer.parseInt(splited[2]),
                                        videos.get(Integer.parseInt(splited[0])),
                                        endpoints.get(Integer.parseInt(splited[1]))));
        }
    }

    public List<Video> getVideos() {
        return videos;
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public List<Cache> getCaches() {
        return caches;
    }

    public List<RequestDescription> getRequestDescriptionList() {
        return requestDescriptionList;
    }
}
