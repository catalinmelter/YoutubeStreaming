package datacenter.services;

import datacenter.models.Pool;
import datacenter.models.Server;
import datacenter.models.Slot;

import static datacenter.components.FileInInfo.fileInInfo;
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReadingService {
    public void parseFile(String fileName){
        String line;
        String[] splited;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            // First Line
            line = br.readLine();
            splited = line.split("\\s+");
            fileInInfo().setRowsLength(parseInt(splited[0]));
            fileInInfo().setSlotsLength(parseInt(splited[1]));
            fileInInfo().setUnavailableSlotsLength(parseInt(splited[2]));
            fileInInfo().setPoolsLength(parseInt(splited[3]));
            fileInInfo().setServersLength(parseInt(splited[4]));

            // Slots unavailable
            for(int i=0; i<fileInInfo().getUnavailableSlotsLength(); i++){
                line = br.readLine();
                splited = line.split("\\s+");
                List<Slot> unavailableSlots = fileInInfo().getUnavailableSlots();
                unavailableSlots.add(new Slot(parseInt(splited[0]), parseInt(splited[1])));
                fileInInfo().setUnavailableSlots(unavailableSlots);
            }

            // Servers
            List<Server> servers = new ArrayList<>();
            for(int i=0; i<fileInInfo().getServersLength(); i++){
                line = br.readLine();
                splited = line.split("\\s+");
                Server server = new Server(i, parseInt(splited[0]), parseInt(splited[1]));
                servers.add(server);
            }
            fileInInfo().setServers(servers);

            // Pools Empty
            List<Pool> pools = new ArrayList<>();
            for(int i=0; i<fileInInfo().getPoolsLength(); i++){
                pools.add(new Pool(i));
            }
            fileInInfo().setPools(pools);

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
