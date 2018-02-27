package youtube.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestDescription {
    private Integer id;
    private Integer requests;
    private Video video;
    private Endpoint endpoint;
}
