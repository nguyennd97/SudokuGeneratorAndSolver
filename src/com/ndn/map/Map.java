package com.ndn.map;

public class Map {
    private int[][] map;
    private int size;
    private int with, height; //number of boxes in a column and a row
    private int withOfBox, heightOfBox; // number of squares in with and height of box

    public Map(int size) {
        this.size = size;
        this.map = new int[size][size];
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
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                set(i, j, 0);
            }
        }
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
}
