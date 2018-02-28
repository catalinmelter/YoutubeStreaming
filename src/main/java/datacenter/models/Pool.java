package datacenter.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Pool {
    private Integer id;
    private List<Server> servers = new ArrayList<>();

    public Pool(Integer id) {
        this.id = id;
    }
}
