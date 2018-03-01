package pizza.services;

import lombok.Getter;
import lombok.Setter;
import pizza.models.Slice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Getter
@Setter
public class FileInfo {
    private Integer rows;
    private Integer columns;
    private Integer minIngredients; //per slice
    private Integer maxCells; //per slice
    private Character[][] pizza;
    private Character[][] pizzaOut;
    private List<Slice> finalSlices = new ArrayList<>();

    private FileInfo() {}
    private static class SingletonHolder {
        static final FileInfo SINGLETON = new FileInfo();
    }
    public static FileInfo getFileInfo(){
        return SingletonHolder.SINGLETON;
    }

    public void logic(){
        Integer r = 0;
        Integer c = 0;

        Slice slice;
        Integer rSecond = 0;
        while (r<rows){
            while (c<columns){
                slice = getSlice(r, c);
                if(slice != null){
                    if(slice.getC2() < columns){
                        c = slice.getC2();
//                        rSecond = slice.getR2() - rSecond;
                    }
                }
//                if(slice == null){
//                    c = c + maxCells - 1;
//                }
                c++;
            }
//            r = r + rSecond + 1;
            r++;
            c=0;
        }
    }

    public void writeFile(String fileName){
        StringBuilder content = new StringBuilder();

        content.append(finalSlices.size());
        content.append("\n");

        for(Slice slice: finalSlices){
            content.append(slice.getR1());
            content.append(" ");
            content.append(slice.getC1());
            content.append(" ");
            content.append(slice.getR2());
            content.append(" ");
            content.append(slice.getC2());
            if(finalSlices.indexOf(slice) != (finalSlices.size()-1)) {
                content.append("\n");
            }
        }

        try {
            Files.write(Paths.get("output_" + fileName), content.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getScore(){
        Integer score = 0;
        for(Slice slice: finalSlices){
            Integer totalOfSlice = (slice.getR2()-slice.getR1()+1) * (slice.getC2()-slice.getC1()+1);
            score += totalOfSlice;
        }
        return score;
    }

    private Slice getSlice(Integer r1, Integer c1){

        Integer cells = 1;
        Optional<Slice> slice = Optional.empty();

        breakWhile:
        while (cells < maxCells) {
            cells++;

            boolean isSquare = isSquare(cells);
            List<Slice> slices = new ArrayList<>();

            for (int k = 0; k <= cells; k++) {
                Integer r2 = r1 + cells - k;
                Integer c2 = c1 + k;
                if(r2 > rows || c2 > columns) break;

                if (k == 0) { //Line
                    r2 -= 1;
                } else if (k == cells) {
                    c2 -= 1;
                } else if (isSquare && ((r2 - r1) == (c2 - c1))) { // Square
                    r2 -= 1;
                    c2 -= 1;
                }

                Integer minimum = getMinimum(r1, c1, r2, c2, cells);
                if (minimum != null){
                    if (minimum == 1){
                        slice = Optional.of(new Slice(r1, c1, r2, c2, minimum));
                        finalSlices.add(slice.get());
                        addPizzaOut(r1, c1, r2, c2);
                        break breakWhile;
                    } else if(minimum > 1){
                        slices.add(new Slice(r1, c1, r2, c2, minimum));
                    }
                }
            }

            //ordered
            if(slices.size() > 0) {
                slice = slices.stream().min(Comparator.comparingInt(Slice::getMinimum));
                slice.ifPresent(finalSlices::add);
                slice.ifPresent(slice1 -> addPizzaOut(slice1.getR1(), slice1.getC1(), slice1.getR2(), slice1.getC2()));
                break;
            }
        }

        return slice.orElse(null);
    }

    private Integer getMinimum(Integer r1, Integer c1, Integer r2, Integer c2, Integer cells){
        try {
            Integer x = r2 - r1 + 1;
            Integer y = c2 - c1 + 1;
            if(x*y > maxCells) return null;

            Integer t = 0;
            Integer m = 0;

            for(int r=r1; r<=r2; r++){
                for(int c=c1; c<=c2; c++){
                    if(pizza[r][c] == 'T') t++;
                    else if(pizza[r][c] == 'M') m++;

                    if(pizzaOut[r][c] != null){
                        return null;
                    }
                }
            }
            if(t<m) return t;
            else return m;
        } catch (Exception e){
            return null;
        }
    }

    private void addPizzaOut(Integer r1, Integer c1, Integer r2, Integer c2){
        for(int r=r1; r<=r2; r++){
            System.arraycopy(pizza[r], c1, pizzaOut[r], c1, c2 + 1 - c1);
        }
    }

    private boolean isSquare(Integer number){
        double square = Math.sqrt(number);
        double x = square - (int)square;
        return (x == 0d);
    }

    private Integer getMinimum(Integer r1, Integer c1, Integer r2, Integer c2){
        Integer t = 0;
        Integer m = 0;
        for (int r=r1; r<=r2; r++){
            for(int c=c1;c<=c2; c++){
                if(pizza[r][c] == 'T') t++;
                else if(pizza[r][c] == 'M') m++;
            }
        }
        if(t<m) return t;
        else return m;
    }

    private boolean isPar(Integer number){
        return (number%2==0);
    }

    private Integer getSquare(Integer number){
        double square = Math.sqrt(number);
        return (int) square;
    }
}
