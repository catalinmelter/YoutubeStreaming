package datacenter.components;

import datacenter.models.Pool;
import datacenter.models.Server;
import datacenter.models.Slot;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
public class FileInInfo {
    private Integer rowsLength;
    private Integer slotsLength;
    private Integer unavailableSlotsLength;
    private Integer poolsLength;
    private Integer serversLength;
    private List<Slot> unavailableSlots = new ArrayList<>();
    private List<Server> servers = new ArrayList<>();
    private List<Pool> pools = new ArrayList<>();
    private Slot[][] slots;

    private FileInInfo(){}
    private static class SingletonHandler{
        static final FileInInfo SINGLETON = new FileInInfo();
    }
    public static FileInInfo fileInInfo(){
        return SingletonHandler.SINGLETON;
    }

    public void logic(){
        //Sort Servers by Capacity and Slots Taken
        servers.sort(Comparator
                .comparing(Server::getCapacity).reversed()
                .thenComparing(Server::getSlotsTaken));

        slots = new Slot[rowsLength][slotsLength];
        for(int i=0;i<rowsLength;i++){
            for(int j=0;j<slotsLength;j++){
                slots[i][j] = new Slot(i, j);
            }
        }

        //Unavailable Slots
        for (Slot slot: unavailableSlots){
            slots[slot.getX()][slot.getY()].setAvailable(false);
        }


        //luam pe rand si verificam daca e liber randul si poolul
        nextServer:
        for (Server server: servers) {

            //line by line
            Map<Integer, Integer> linesPerformanceMap = new HashMap<>();
            Map<Integer, Integer> poolsPerformanceMap = new HashMap<>();

            //lines orderd by performance de la cel mai mic la cel mai mare
            for(int currentLine=0; currentLine<slots.length; currentLine++){
                linesPerformanceMap.put(currentLine, getServersPerformanceOfTheLine(slots[currentLine]));
            }
            linesPerformanceMap = linesPerformanceMap.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Integer>comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));

            //pool ordered by performance
            for(Pool pool: pools){
                poolsPerformanceMap.put(pool.getId(), getPoolPerformance(pool));
            }
            poolsPerformanceMap = poolsPerformanceMap.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Integer>comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));



            for (Map.Entry<Integer, Integer> linePerformance : linesPerformanceMap.entrySet()) {
                Integer line = linePerformance.getKey();
                for (Map.Entry<Integer, Integer> poolPerformance : poolsPerformanceMap.entrySet()) {
                    Integer poolId = poolPerformance.getKey();

                    for (int column = 0; column < slots[line].length; column++) {
                        //slot available
                        if (slots[line][column].isAvailable() && slots[line][column].getServer() == null) {
                            //is space
                            Integer slotsAvailable = getSlotsAvailable(line, column);
                            if(slotsAvailable>=server.getSlotsTaken()){
                                //add
                                server.setSlot(slots[line][column]);
                                pools.get(poolId).getServers().add(server);
                                server.setPool(pools.get(poolId));
                                slots[line][column].setOffset(true);
                                for(int k=0; k<server.getSlotsTaken(); k++){
                                    slots[line][column+k].setAvailable(false);
                                    slots[line][column+k].setServer(server);
                                }
                                showSlots();

                                continue nextServer;
                            }
                        }
                    }
                }
            }
        }


        System.out.println();
    }

    private Integer getSlotsAvailable(Integer line, Integer column){
        Integer slotsAvailable = 0;
        for(int i=column; i<slots[line].length; i++){
            if(slots[line][i].isAvailable() && slots[line][i].getServer()==null){
                slotsAvailable ++;
            }
        }
        return slotsAvailable;
    }

    private Integer getServersPerformanceOfTheLine (Slot[] lineSlotes){
        Integer serversPerformance = 0;
        for(Slot slot: lineSlotes){
            if(slot.getServer() != null){
                serversPerformance += slot.getServer().getCapacity();
            }
        }
        return serversPerformance;
    }

    private Integer getPoolPerformance(Pool pool){
        List<Server> servers = pool.getServers();
        Integer performance = 0;
        for(Server server: servers){
            performance += server.getCapacity();
        }
        return performance;
    }

    private void showSlots(){
        for (Slot[] slot : slots) {
            for (Slot aSlot : slot) {
                if (aSlot.isAvailable()) {
                    System.out.print("_ ");
                } else if (aSlot.isOffset()) {
                    System.out.print(String.valueOf(aSlot.getServer().getId()) + " ");
                } else if (!aSlot.isAvailable() && aSlot.getServer() == null) {
                    System.out.print("X ");
                } else {
                    System.out.print(String.valueOf(aSlot.getServer().getId()) + " ");
                }
            }
            System.out.println();
        }
    }

    public void score(){
        Integer[][] scoreMatrix = new Integer[pools.size()][slots.length];

        for(int poolId=0; poolId<pools.size(); poolId++){
            Pool pool = pools.get(poolId);

            for(int line=0; line<slots.length; line++){
                Slot[] slotsLine = slots[line];
                for (Slot slot: slotsLine){
                    if(pool.equals(slot.getServer().getPool())){

                    }
                }

            }
        }
    }
}
