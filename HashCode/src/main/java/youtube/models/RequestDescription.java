package youtube.models;

public class RequestDescription implements Comparable<RequestDescription> {
    private Integer requests;
    private Video video;
    private Endpoint endpoint;

    public RequestDescription(Integer requests, Video video, Endpoint endpoint) {
        this.requests = requests;
        this.video = video;
        this.endpoint = endpoint;
    }

    public Integer getRequests() {
        return requests;
    }

    public Video getVideo() {
        return video;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public int compareTo(RequestDescription r){
        if (requests > r.requests) {
            return -1;
        } else if (requests <  r.requests) {
            return 1;
        } else {
            return 0;
        }
    }
}
