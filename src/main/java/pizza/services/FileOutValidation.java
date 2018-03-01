package pizza.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileOutValidation {
    private Integer slicesLength;
    private Character[][] pizzaOut = new Character[FileInfo.getFileInfo().getRows()][FileInfo.getFileInfo().getColumns()];

    private FileOutValidation() {}
    private static class SingletonHolder {
        static final FileOutValidation SINGLETON = new FileOutValidation();
    }
    public static FileOutValidation getFileInfo(){
        return SingletonHolder.SINGLETON;
    }

    public boolean validation(String fileName){
        String line;
        String[] splited;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            // First Line
            line = br.readLine();
            splited = line.split("\\s+");
            slicesLength = Integer.parseInt(splited[0]);

            for(int i=0; i<slicesLength; i++){
                line = br.readLine();
                splited = line.split("\\s+");
                Integer r1 = Integer.parseInt(splited[0]);
                Integer c1 = Integer.parseInt(splited[1]);
                Integer r2 = Integer.parseInt(splited[2]);
                Integer c2 = Integer.parseInt(splited[3]);

                Integer rosii = 0;
                Integer mush = 0;
                for(int r=r1; r<=r2; r++){
                    for(int c=c1; c<=c2; c++){
                        if(pizzaOut[r][c] == null){
                            Character ch = FileInfo.getFileInfo().getPizza()[r][c];
                            if (ch == 'M') {
                                mush++;
                            }else if(ch == 'T'){
                                rosii++;
                            }
                            pizzaOut[r][c] = FileInfo.getFileInfo().getPizza()[r][c];
                        }else {
                            return false;
                        }
                    }
                }
                if(rosii==0 || mush==0){
                    return false;
                }
            }

            return true;
        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
