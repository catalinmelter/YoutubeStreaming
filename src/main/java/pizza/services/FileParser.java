package pizza.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static pizza.services.FileInfo.getFileInfo;

public class FileParser {

    private FileParser() {}
    private static class SingletonHolder {
        static final FileParser SINGLETON = new FileParser();
    }
    public static FileParser getFileParser(){
        return SingletonHolder.SINGLETON;
    }

    public void parse(String fileName){
        String line;
        String[] splited;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            // First Line
            line = br.readLine();
            splited = line.split("\\s+");
            getFileInfo().setRows(parseInt(splited[0]));
            getFileInfo().setColumns(parseInt(splited[1]));
            getFileInfo().setMinIngredients(parseInt(splited[2]));
            getFileInfo().setMaxCells(parseInt(splited[3]));

            Character[][] pizza = new Character[getFileInfo().getRows()][getFileInfo().getColumns()];
            getFileInfo().setPizza(pizza);

            Character[][] pizzaOut = new Character[getFileInfo().getRows()][getFileInfo().getColumns()];
            getFileInfo().setPizzaOut(pizzaOut);

            for(int i=0; i<getFileInfo().getRows(); i++){
                line = br.readLine();

                for(int j=0; j<getFileInfo().getColumns(); j++){
                    getFileInfo().getPizza()[i][j] = line.charAt(j);
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
