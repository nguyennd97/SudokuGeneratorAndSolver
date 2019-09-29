package com.ndn.map;

import com.ndn.Util;

// copy of: https://github.com/noah978/Java-Sudoku-Generator
public class BacktrackingSolution {
    public int numberOfSolutions = 0;
    public int counter = 0;
    public Map solution;
    private int[] root;

    public BacktrackingSolution(){

    }

    public BacktrackingSolution(int size){
        root = new int[size];
        for(int i = 0; i < size; i++) root[i] = i;
    }


    public boolean checkForOneSolution(Map map) {
        return checkForOneSolution(0, 0, map);
    }

    public boolean checkForOneSolution(int r, int c, Map map) {
        counter++;
        if (numberOfSolutions >= 2) {
            return true;
        }

        if (r >= map.size()) {
            numberOfSolutions++;
            this.solution = map.cloneMap();
            return false;
        }
        if (c >= map.size()) {
            return checkForOneSolution(r + 1, 0, map);
        }
        if (map.get(r, c) != -1) {
            return checkForOneSolution(r, c + 1, map);
        }
        int num = -1;
        while (true) {
            map.set(r, c, -1);
            num++;
            if (num >= map.size()) {
                return true;
            }
            if (checkSquare(r, c, num, map) && checkRow(r, num, map) && checkColumn(c, num, map)) {
                map.set(r, c, num);
                checkForOneSolution(r, c + 1, map);
            }
        }
    }

    public boolean randomNewMap(int r, int c, Map map){
        if (numberOfSolutions >= 1) {
            return true;
        }
        counter++;
        if (r >= map.size()) {
            numberOfSolutions++;
            this.solution = map.cloneMap();
            return false;
        }
        if (c >= map.size()) {
            return randomNewMap(r + 1, 0, map);
        }
        if (map.get(r, c) != -1) {
            return randomNewMap(r, c + 1, map);
        }
        int num = -1;
        int[] shuffle = Util.shuffleUp(root);
        while (true) {
            map.set(r, c, -1);
            num++;
            if (num >= map.size()) {
                return true;
            }
            if (checkSquare(r, c, shuffle[num], map) && checkRow(r, shuffle[num], map) && checkColumn(c, shuffle[num], map)) {
                map.set(r, c, shuffle[num]);
                randomNewMap(r, c + 1, map);
            }
        }
    }

    private boolean checkSquare(int r, int c, int num, Map map) {
        for (int i = map.rowOfBoxesContainRow(r); i < map.rowOfBoxesContainRow(r) + map.getHeightOfBox(); i++) {
            for (int j = map.colOfBoxesContainCol(c); j < map.colOfBoxesContainCol(c) + map.getWithOfBox(); j++) {
                if (map.get(i, j) == num)
                    return false;
            }
        }
        return true;
    }

    private boolean checkRow(int r, int num, Map map) {
        for (int value : map.getMap()[r])
            if (value == num)
                return false;
        return true;
    }

    private boolean checkColumn(int c, int num, Map map) {
        for (int[] row : map.getMap())
            if (row[c] == num)
                return false;
        return true;
    }
}
