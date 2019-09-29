package com.ndn;

import com.ndn.map.*;

import java.util.Random;

public class Generator {
    public static Map generateRandomMap(int size, long timeToGen, int minScore, int maxScore) {
        if(minScore > maxScore) throw new IllegalArgumentException("Min score is bigger than max score");
        Random random = new Random();
        Map map = MapFactory.random(size);
        Map answer = map.cloneMap();
        boolean[][] triedToRemove = new boolean[map.size()][map.size()];
        int tried = 0;
        long start_time = System.currentTimeMillis();
        while (System.currentTimeMillis() - start_time < timeToGen) {
            if (tried == map.size() * map.size()) break;
            Map backup = map.cloneMap();
            int row = 0, col = 0;
            do {
                row = random.nextInt(map.size());
                col = random.nextInt(map.size());
            } while (map.get(row, col) == -1 || triedToRemove[row][col]);
            int lastVal = map.get(row, col);
            triedToRemove[row][col] = true;
            map.set(row, col, -1);
            PseudoMap pseudoMap = new PseudoMap(map);
            pseudoMap.solve(start_time, timeToGen);
            if (pseudoMap.errorFound() || !pseudoMap.solved()) {
                map.set(row, col, lastVal);
            }
            tried++;
            int score = map.difficultyScore(answer);
            if(score > maxScore) {
                map = backup;
                break;
            }
        }
        map.setScore(map.difficultyScore(answer));
        map.setSolution(answer);
//        System.out.println("Time: " + (System.currentTimeMillis() - start_time) + " ms");
        return map;
    }
}
