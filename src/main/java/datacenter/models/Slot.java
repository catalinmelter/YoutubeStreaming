package datacenter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Slot {
    private Integer x;
    private Integer y;
    private Server server;
    private boolean available = true;
    private boolean offset = false;

    public Slot(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}
