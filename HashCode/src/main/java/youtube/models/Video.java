package youtube.models;

public class Video {
    private Integer id;
    private Integer mb;

    public Video(Integer id, Integer mb) {
        this.id = id;
        this.mb = mb;
    }

    public Integer getMb() {
        return mb;
    }

    public Integer getId() {
        return id;
    }
}
