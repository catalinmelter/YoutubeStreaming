package youtube.component;

import youtube.models.Cache;
import youtube.models.Endpoint;
import youtube.models.RequestDescription;
import youtube.models.Video;

import java.util.*;

public class YoutubeStreamingOutput {
    private Map<Cache, Set<Video>> videoCacheMap = new HashMap<>();

    private YoutubeStreamingOutput(){}

    public void setValues(){
        videoCacheMap.put(YoutubeStreaming.getInstance().getCaches().get(0),
                Collections.singleton(YoutubeStreaming.getInstance().getVideos().get(2)));
        videoCacheMap.put(YoutubeStreaming.getInstance().getCaches().get(1),
                new HashSet<>(Arrays.asList(YoutubeStreaming.getInstance().getVideos().get(1),
                        YoutubeStreaming.getInstance().getVideos().get(3))));
        videoCacheMap.put(YoutubeStreaming.getInstance().getCaches().get(2),
                new HashSet<>(Arrays.asList(YoutubeStreaming.getInstance().getVideos().get(0),
                        YoutubeStreaming.getInstance().getVideos().get(1))));
    }

    private static class SingletonHolder {
        static final YoutubeStreamingOutput INSTANCE = new YoutubeStreamingOutput();
    }

    public static YoutubeStreamingOutput getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void setVideoCacheMap(){
        Collections.sort(YoutubeStreaming.getInstance().getRequestDescriptionList());
        List<RequestDescription> r = YoutubeStreaming.getInstance().getRequestDescriptionList();

        //ordered byb requests
        for (RequestDescription requestDescription: YoutubeStreaming.getInstance().getRequestDescriptionList()){
            Endpoint endpoint = requestDescription.getEndpoint();
            Video video = requestDescription.getVideo();

            //ordered by latency
            Map<Cache, Integer> cachesLatencyMap = endpoint.getCachesLatency();

            for (Map.Entry<Cache, Integer> cacheLatency : cachesLatencyMap.entrySet()) {
                //if cache is available
                if (cacheLatency.getKey().getMb() >= video.getMb()){
                    cacheLatency.getKey().setMb(cacheLatency.getKey().getMb()-video.getMb());
                    Set<Video> videos = videoCacheMap.get(cacheLatency.getKey());
                    if(videos == null) videos = new HashSet<>();
                    videos.add(video);
                    videoCacheMap.put(cacheLatency.getKey(), videos);
                    break;
                }
            }
        }
    }

    public double getScore(){
        double allSavings = 0d;
        double allRequests = 0d;
        for(RequestDescription requestDescription: YoutubeStreaming.getInstance().getRequestDescriptionList()){
            Integer requests = requestDescription.getRequests();
            Video video = requestDescription.getVideo();
            Endpoint endpoint = requestDescription.getEndpoint();
            Cache cache = null;
            for (Map.Entry<Cache, Set<Video>> cacheVideo : videoCacheMap.entrySet()) {
                Set<Video> videos = cacheVideo.getValue();
                for(Video videoIn: videos){
                    if (videoIn.equals(video) && endpoint.getCachesLatency().containsKey(cacheVideo.getKey())){
                        if(cache == null){
                            cache = cacheVideo.getKey();
                        }else if(endpoint.getCachesLatency().get(cache) > endpoint.getCachesLatency().get(cacheVideo.getKey())) {
                            cache = cacheVideo.getKey();
                        }
                    }
                }
            }

            double latencySaving = 0d;
            try {
                if(cache != null) {
                    double datacenterLatency = endpoint.getDatacenterLatency();
                    double cacheLatency = endpoint.getCachesLatency().get(cache);
                    latencySaving = datacenterLatency - cacheLatency;
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }

            allSavings += (latencySaving * requests);
            allRequests += requests;
        }

        return  (allSavings / allRequests) * 1000;
    }

    public void validation(){
        for (Map.Entry<Cache, Set<Video>> videoCache : videoCacheMap.entrySet()) {
            System.out.print(videoCache.getKey().getId());
            System.out.print(" ");
            Set<Video> videos = videoCache.getValue();
            for(Video video: videos){
                System.out.print(video.getId());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
