package pizza.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Slice {
    private Integer r1;
    private Integer c1;
    private Integer r2;
    private Integer c2;
    private Integer minimum;
}
