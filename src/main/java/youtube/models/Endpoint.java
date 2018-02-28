package youtube.models;

import lombok.Getter;

import java.util.*;

public class Endpoint {
    private Integer id;
    private Integer datacenterLatency;
    private Map<Cache, Integer> cachesLatency = new HashMap<>();

    public Endpoint(Integer id, Integer datacenterLatency) {
        this.id = id;
        this.datacenterLatency = datacenterLatency;
    }

    public void addCacheLatency(Cache cache, Integer latency){
        cachesLatency.put(cache, latency);
    }

    public Integer getDatacenterLatency() {
        return datacenterLatency;
    }

    public Map<Cache, Integer> getCachesLatency() {
        return cachesLatency;
    }

    public void sortByValue() {

        // 1. Convert Map to List of Map
        List<Map.Entry<Cache, Integer>> list =
                new LinkedList<Map.Entry<Cache, Integer>>(this.cachesLatency.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Cache, Integer> sortedMap = new LinkedHashMap<Cache, Integer>();
        for (Map.Entry<Cache, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        this.cachesLatency = sortedMap;
    }

    public Integer getId() {
        return id;
    }
}
