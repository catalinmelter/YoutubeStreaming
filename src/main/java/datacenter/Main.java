package datacenter;

import datacenter.components.FileInInfo;
import datacenter.services.FileReadingService;

public class Main {
    public static void main(String[] args) {
        String fileName = "datacenter.txt";

        FileReadingService service = new FileReadingService();
        service.parseFile(fileName);

        FileInInfo.fileInInfo().logic();
        FileInInfo.fileInInfo().writeFile(fileName);
        System.out.println("Score: " + FileInInfo.fileInInfo().getScore());
    }
}
