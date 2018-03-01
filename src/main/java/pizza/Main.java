package pizza;

import pizza.services.FileOutValidation;

import static pizza.services.FileParser.getFileParser;
import static pizza.services.FileInfo.getFileInfo;

public class Main {
    public static void main(String[] args) {
        String fileName = "big.in";
        getFileParser().parse(fileName);
        getFileInfo().logic();
        getFileInfo().writeFile(fileName);
        Integer score = getFileInfo().getScore();
        Integer totalScore = getFileInfo().getRows() * getFileInfo().getColumns();
        System.out.println("Score: " + score);
        System.out.println("Total Score: " + totalScore);
        System.out.println("Wasted Cells: " + (totalScore-score));
        if(FileOutValidation.getFileInfo().validation("output_" + fileName)){
            System.out.println("Output File Is Valid!");
        }else{
            System.out.println("Output File Is Not Valid!!!!!!!");
        }
    }
}
