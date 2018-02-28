package datacenter.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Server {
    private Integer id;
    private Integer slotsTaken;
    private Integer capacity;
    private Slot slot;
    private Pool pool;

    public Server(Integer id, Integer slotsTaken, Integer capacity) {
        this.id = id;
        this.slotsTaken = slotsTaken;
        this.capacity = capacity;
    }
}
