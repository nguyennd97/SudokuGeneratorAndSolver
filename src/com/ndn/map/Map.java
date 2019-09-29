package com.ndn.map;

import java.util.Arrays;

public class Map {
    private int[][] map;
    private int size;
    private int with, height; //number of boxes in a column and a row
    private int withOfBox, heightOfBox; // number of squares in with and height of box
    private int score = 0;
    private Map solution;

    public Map(int size) {
        this.size = size;
        this.map = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(this.map[i], -1);
        }
    }

    public int size() {
        return size;
    }

    public int get(int row, int col) {
        return map[row][col];
    }

    public void set(int row, int col, int value) {
        this.map[row][col] = value;
    }

    public int box(int row, int col) {
        return 0;
    }

    public int getWith() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public int getWithOfBox() {
        return 0;
    }

    public int getHeightOfBox() {
        return 0;
    }

    public Map random() {
        return null;
    }

    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                set(i, j, -1);
            }
        }
    }

    public Map getSolution() {
        return solution;
    }

    public void setSolution(Map solution) {
        this.solution = solution;
    }

    public boolean sameBox(int row1, int col1, int row2, int col2) {
        return box(row1, col1) == box(row2, col2);
    }

    public Map cloneMap() {
        return null;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int[][] getMap() {
        return map;
    }

    public int lowestRowOfBox(int box) {
        return 0;
    }

    public int lowestColOfBox(int box) {
        return 0;
    }

    public int rowOfBoxesContainRow(int row) {
        return lowestRowOfBox(box(row, 0));
    }

    public int colOfBoxesContainCol(int col) {
        return lowestColOfBox(box(0, col));
    }

    public int difficultyScore(Map answer) {
        return 0;
    }

    public boolean full() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (get(row, col) == -1) return false;
            }
        }
        return true;
    }

    public int numberOfEmptySquares() {
        int count = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (get(row, col) == -1) count++;
            }
        }
        return count;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String code() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (get(row, col) == -1) builder.append("00");
                else if (get(row, col) < 10) builder.append("0").append(get(row, col));
                else {
                    builder.append(get(row, col));
                }
            }
        }
        return builder.toString();
    }
}
