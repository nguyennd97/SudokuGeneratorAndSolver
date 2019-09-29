package com.ndn.map;

public class Map5 extends Map{
    private int[][] boxes = {
            {0, 0, 0, 1, 1},
            {0, 0, 2, 1, 1},
            {3, 2, 2, 2, 1},
            {3, 3, 2, 4, 4},
            {3, 3, 4, 4, 4}
    };

    public Map5() {
        super(5);
    }


    @Override
    public Map cloneMap() {
        Map map = new Map5();
        for(int row = 0; row < map.size(); row++) {
            for(int col = 0; col < map.size(); col++) {
                map.set(row, col, this.get(row, col));
            }
        }
        return map;
    }

    @Override
    public int box(int row, int col) {
        return this.boxes[row][col];
    }

    @Override
    public Map random() {
        reset();
        BacktrackingSolution solution = new BacktrackingSolution(this.size());
        solution.randomNewMap(0, 0, this);
        if(solution.numberOfSolutions == 1) {
            this.setMap(solution.solution.getMap());
        }
        return this;
    }

    @Override
    public int difficultyScore(Map answer) {
        Map question = cloneMap();
        int score = 0;
        while (!full()) {
            int minChoicesOfAllSquares = Integer.MAX_VALUE;
            int minRow = 0, minCol = 0;
            for (int row = 0; row < size(); row++) {
                for (int col = 0; col < size(); col++) {
                    if (get(row, col) != -1) continue;
                    boolean[] existed = new boolean[size()];

                    for (int r = 0; r < size(); r++) {
                        if (r == row) continue;
                        if (get(r, col) == -1) continue;
                        existed[get(r, col)] = true;
                    }
                    for (int c = 0; c < size(); c++) {
                        if (c == col) continue;
                        if (get(row, c) == -1) continue;
                        existed[get(row, c)] = true;
                    }
                    for (int r = 0; r < size(); r++) {
                        for (int c = 0; c < size(); c++) {
                            if(!sameBox(r, c, row, col)) continue;
                            if (r == row || c == col) continue;
                            if (get(r, c) == -1) continue;
                            existed[get(r, c)] = true;
                        }
                    }
                    int s = 0;
                    for (boolean b : existed) {
                        s += b ? 0 : 1;
                    }
                    if (minChoicesOfAllSquares > s) {
                        minChoicesOfAllSquares = s;
                        minRow = row;
                        minCol = col;
                    }
                }
            }
            if (minChoicesOfAllSquares != Integer.MAX_VALUE) {
                score += (minChoicesOfAllSquares - 1) * (minChoicesOfAllSquares - 1) * 100;
                set(minRow, minCol, answer.get(minRow, minCol));
            }
        }

        this.setMap(question.getMap());
        score += numberOfEmptySquares();
        return score;
    }

    @Override
    public String toString(){

        return "+-----------+-------+" + System.getProperty("line.separator") +
                "| " + getSymbol(0, 0) + "   " + getSymbol(0, 1) + "   " + getSymbol(0, 2) + " | " + getSymbol(0, 3) + "   " + getSymbol(0, 4) + " |" + System.getProperty("line.separator") +
                "|       +---+       |" + System.getProperty("line.separator") +
                "| " + getSymbol(1, 0) + "   " + getSymbol(1, 1) + " | " + getSymbol(1, 2) + " | " + getSymbol(1, 3) + "   " + getSymbol(1, 4) + " |" + System.getProperty("line.separator") +
                "+---+---+   +---+   |" + System.getProperty("line.separator") +
                "| " + getSymbol(2, 0) + " | " + getSymbol(2, 1) + "   " + getSymbol(2, 2) + "   " + getSymbol(2, 3) + " | " + getSymbol(2, 4) + " |" + System.getProperty("line.separator") +
                "|   +---+   +---+---+" + System.getProperty("line.separator") +
                "| " + getSymbol(3, 0) + "   " + getSymbol(3, 1) + " | " + getSymbol(3, 2) + " | " + getSymbol(3, 3) + "   " + getSymbol(3, 4) + " |" + System.getProperty("line.separator") +
                "|       +---+       |" + System.getProperty("line.separator") +
                "| " + getSymbol(4, 0) + "   " + getSymbol(4, 1) + " | " + getSymbol(4, 2) + "   " + getSymbol(4, 3) + "   " + getSymbol(4, 4) + " |" + System.getProperty("line.separator") +
                "+-------+-----------+" + System.getProperty("line.separator");
    }

    private String getSymbol(int row, int col) {
        int n = get(row, col) + 1;
        if (n == 0) return ".";
        return String.valueOf(n);
    }
}
