package youtube.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Endpoint {
    private Integer id;
    private Integer datacenterLatency;
    private Map<Cache, Integer> cachesLatency;
}
