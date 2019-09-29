package com.ndn;

import com.ndn.map.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;

public class Run {
    public static void main(String... args) {
        // properties
        int level = 8;
        int numberOfLessons = 100;
        int minScore = 800;
        int maxScore = 1000000;
        String path = "sudoku8x8\\Level_5.txt";

        //cac cac cac
        ArrayList<Map> maps = new ArrayList<>();
        while (numberOfLessons > 0) {
            Map map = Generator.generateRandomMap(level, 2000, minScore, maxScore);
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
            out.append(System.getProperty("line.separator"));
            out.append("Lesson >> ").append(index++);
            out.append(System.getProperty("line.separator"));
            out.append("Difficulty score: ").append(map.getScore());
            out.append(System.getProperty("line.separator"));
            out.append(map.toString());
            out.append(System.getProperty("line.separator"));
            out.append(map.getSolution().toString());
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("C:\\Users\\NguyenDangNguyen\\Desktop\\" + path), StandardCharsets.UTF_8))) {
            writer.write(out.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
