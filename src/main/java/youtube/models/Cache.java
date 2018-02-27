package youtube.models;

public class Cache {
    private Integer id;
    private Integer mb;

    public Cache(Integer id, Integer mb) {
        this.id = id;
        this.mb = mb;
    }

    public Integer getMb() {
        return mb;
    }

    public Integer getId() {
        return id;
    }

    public void setMb(Integer mb){
        this.mb = mb;
    }
}
