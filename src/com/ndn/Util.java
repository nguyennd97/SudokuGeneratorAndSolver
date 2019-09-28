package com.ndn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Util {
    public static Random random = new Random();

    public static void permutation(ArrayList<Integer> set, ArrayList<ArrayList<Integer>> result) {
        permutation(new ArrayList<>(), set, result);
    }

    private static void permutation(ArrayList<Integer> prefix, ArrayList<Integer> set, ArrayList<ArrayList<Integer>> result) {
        int n = set.size();
        if (n == 0) result.add(prefix);
        else {
            Loop: for (int i = 0; i < n; i++) {
                ArrayList<Integer> p = new ArrayList<>(prefix);
                p.add(set.get(i));

                for(ArrayList<Integer> r : result) {
                    for(int j = 0; j < Math.min(p.size(), r.size()); j++) {
                        if(r.get(j) == p.get(j)) continue Loop;
                    }
                }
                ArrayList<Integer> s = new ArrayList<>();
                s.addAll(set.subList(0, i));
                s.addAll(set.subList(i + 1, n));
                permutation(p, s, result);
            }
        }
    }

    public static ArrayList<Integer> randomSet(int amount, int max) {
        Random random = new Random();
        boolean[] existed = new boolean[max];
        ArrayList<Integer> set = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            int val = 0;
            do {
                val = random.nextInt(max);
            } while (existed[val]);
            existed[val] = true;
            set.add(val);
        }
        return set;
    }

    public static int[] shuffleUp(int[] values) {
        ArrayList<Integer> tmp = new ArrayList<>();
        for(int i : values) tmp.add(i);
        int[] shuffle = new int[values.length];
        for(int i = 0; i < values.length; i++) {
            int c = random.nextInt(tmp.size());
            shuffle[i] = tmp.get(c);
            tmp.remove(c);
        }
        return shuffle;
    }
}
