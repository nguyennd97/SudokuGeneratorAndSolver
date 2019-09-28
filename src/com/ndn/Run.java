package com.ndn;

import com.ndn.map.*;

public class Run {
    public static void main(String... args) {
//        System.out.println(new Map4().random());
//        System.out.println(new Map6().random());
//        System.out.println(new Map8().random());
//        System.out.println(new Map9().random());
//        System.out.println(new Map12().random());
//        System.out.println(new Map16().random());
//        System.out.println(new Map25().random());
//        Map map = Generator.generateRandomMap(25);
//        System.out.println(map);
//        System.out.println(map.getScore());
        int[][] answer = {
                {0, 0, 3, 0, 0, 0, 0, 0, 0},
                {8, 0, 9, 4, 6, 0, 7, 0, 2},
                {2, 0, 0, 0, 1, 8, 6, 0, 0},
                {0, 0, 0, 0, 0, 6, 0, 7, 0},
                {0, 0, 8, 0, 0, 0, 4, 0, 0},
                {0, 7, 0, 8, 0, 0, 0, 0, 0},
                {0, 0, 2, 9, 4, 0, 0, 0, 5},
                {4, 0, 6, 0, 3, 2, 8, 0, 7},
                {0, 0, 0, 0, 0, 0, 2, 0, 0},
        };

        for(int[] i : answer) {
            for(int j = 0; j < i.length; j++) i[j] -= 1;
        }
        Map map = new Map9();
        map.setMap(answer);

        int[][] question = {
                {7, 6, 3, 2, 9, 5, 1, 8, 4},
                {8, 1, 9, 4, 6, 3, 7, 5, 2},
                {2, 4, 5, 7, 1, 8, 6, 9, 3},
                {3, 2, 4, 1, 5, 6, 9, 7, 8},
                {6, 5, 8, 3, 7, 9, 4, 2, 1},
                {9, 7, 1, 8, 2, 4, 5, 3, 6},
                {1, 8, 2, 9, 4, 7, 3, 6, 5},
                {4, 9, 6, 5, 3, 2, 8, 1, 7},
                {5, 3, 7, 6, 8, 1, 2, 4, 9},
        };

        for(int[] i : question) {
            for(int j = 0; j < i.length; j++) i[j] -= 1;
        }


        Map map1 = new Map9();
        map1.setMap(question);

//        System.out.println(map);
//        PseudoMap pseudoMap = new PseudoMap(map);
//        pseudoMap.solve();
//        System.out.println(pseudoMap.getResult());

        while (true) {
            map = Generator.generateRandomMap(16);
            System.out.println(map);
            System.out.println(map.getScore());
        }
    }
}
