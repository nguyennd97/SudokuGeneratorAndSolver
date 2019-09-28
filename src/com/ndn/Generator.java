package com.ndn;

import com.ndn.map.Map;
import com.ndn.map.Map4;
import com.ndn.map.MapFactory;

import java.util.Random;

public class Generator {
    private static long maxTime = 5000;

    public static Map generateRandomMap(int size) {
        Random random = new Random();
        Map map = MapFactory.random(size);
        Map answer = map.cloneMap();
        boolean[][] triedToRemove = new boolean[map.size()][map.size()];
        int tried = 0;
        long start_time = System.currentTimeMillis();
        while (System.currentTimeMillis() - start_time < maxTime) {
            if (tried == map.size() * map.size()) break;
            int row = 0, col = 0;
            do {
                row = random.nextInt(map.size());
                col = random.nextInt(map.size());
            } while (map.get(row, col) == -1 || triedToRemove[row][col]);
            int lastVal = map.get(row, col);
            triedToRemove[row][col] = true;
            map.set(row, col, -1);
//            System.out.println("init:\n" + map);
            PseudoMap pseudoMap = new PseudoMap(map);
            pseudoMap.solve();
            if (pseudoMap.errorFound() || !pseudoMap.solved()) {
                map.set(row, col, lastVal);
//                System.out.println("error found. Backup:\n" + map);
            } else {
//                System.out.println("Solved:\n" + pseudoMap);
            }
            tried++;
        }
        map.setScore(map.difficultyScore(answer));
        return map;
    }
}
