package youtube;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String fileName = "file.txt";

        //read file into stream, try-with-resources
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            lines.forEach(
                    line -> {
                        String[] splited = line.split("\\s+");
                        for (String string:splited) {
                            Integer number = Integer.parseInt(string);
                            System.out.println(number);
                        }
                    }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
