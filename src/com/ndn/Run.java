package com.ndn;

import com.google.api.detect.Detect;
import com.google.api.detect.DetectResult;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.ndn.map.*;
import com.sun.java.accessibility.util.Translator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// generates sudoku puzzles and print to file text.
public class Run {

    static void randomAndSaveToFile(){
        // properties
        int level = 16;
        int numberOfLessons = 100;
        int minScore = 1;
        int maxScore = 10000000;
        String path = "result/sudoku16x16/Gen.txt";

        //cac cac cac
        ArrayList<Map> maps = new ArrayList<>();
        while (numberOfLessons > 0) {
            Map map = Generator.generateRandomMap(level, 5000, minScore, maxScore);
            System.out.println(map.getScore());
            System.out.println(map);
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
    public static void main(String... args) throws Exception {
        randomAndSaveToFile();
    }
}
