package com.ndn;

import com.ndn.map.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;

// generates sudoku puzzles and print to file text.
public class Run {
    public static void main(String... args) throws UnsupportedEncodingException {
        // properties
        int level = 9;
        int numberOfLessons = 100;
        int minScore = 21;
        int maxScore = 100;
        String path = "result/sudoku9x9/Level_2.txt";

        //cac cac cac
        ArrayList<Map> maps = new ArrayList<>();
        while (numberOfLessons > 0) {
            Map map = Generator.generateRandomMap(level, 10000, minScore, maxScore);
            if(map.getScore() >= minScore && map.getScore() <= maxScore) {
                maps.add(map);
                numberOfLessons--;
                System.out.println(numberOfLessons + " left");
            }
        }
        Comparator<Map> comparator = (o1, o2) -> {
            if(o1.getScore() == o2.getScore()) return 0;
            return o1.getScore() > o2.getScore() ? 1 : -1;
        };
        maps.sort(comparator);
        int index = 1;
        StringBuilder out = new StringBuilder();
        for(Map map : maps) {
            out.append(Util.newLine);
            out.append("Lesson >> ").append(index++);
            out.append(Util.newLine);
            out.append("Difficulty score: ").append(map.getScore());
            out.append(Util.newLine);
            out.append(map.toString());
            out.append(Util.newLine);
            out.append(map.getSolution().toString());
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), StandardCharsets.UTF_8))) {
            writer.write(out.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
