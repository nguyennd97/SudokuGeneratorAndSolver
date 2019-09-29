package com.ndn.map;

import com.ndn.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// 4x4, 6x6, 8x8, 9x9, 12x12, 16x16, 25x25
public class MapType1 extends Map {
    Random random = new Random();

    MapType1(int size) {
        super(size);
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
                    for (int r = rowOfBoxesContainRow(row); r < rowOfBoxesContainRow(row) + getHeightOfBox(); r++) {
                        for (int c = colOfBoxesContainCol(col); c < colOfBoxesContainCol(col) + getWithOfBox(); c++) {
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
    public int box(int row, int col) {
        return ((int) (row / getHeightOfBox())) * getWith() + (int) (col / getWithOfBox());
    }

    @Override
    public Map random() {
        reset();
        boolean[][] boxExisted = new boolean[getHeight() * getWith()][size()];
        boolean[][] rowExisted = new boolean[size()][size()];
        boolean[][] columnExisted = new boolean[size()][size()];
        for (int i = 0; i < getWith(); i++) {
            while (true) {
                /* backup */
                boolean[][] _boxExisted = new boolean[getHeight() * getWith()][size()];
                boolean[][] _rowExisted = new boolean[size()][size()];
                boolean[][] _columnExisted = new boolean[size()][size()];
                for (int ix = 0; ix < getHeight() * getWith(); ix++) {
                    if (size() >= 0) System.arraycopy(boxExisted[ix], 0, _boxExisted[ix], 0, size());
                }
                for (int ix = 0; ix < size(); ix++) {
                    if (size() >= 0) System.arraycopy(rowExisted[ix], 0, _rowExisted[ix], 0, size());
                    if (size() >= 0) System.arraycopy(columnExisted[ix], 0, _columnExisted[ix], 0, size());
                }
                Map map = this.cloneMap();

                /* random board */
                boolean found = randomBox(i, boxExisted, rowExisted, columnExisted);

                if (found) break;

                /* if random fail, backup data and continue */
                this.setMap(map.getMap());
                boxExisted = _boxExisted;
                rowExisted = _rowExisted;
                columnExisted = _columnExisted;
            }
        }

        // test
        Random random = new Random();
        for (int row = this.getHeightOfBox(); row < size(); row++) {
            int val = 0;
            do {
                val = random.nextInt(size());
            } while (columnExisted[0][val]);
            this.set(row, 0, val);
            columnExisted[0][val] = true;
            rowExisted[row][val] = true;
            boxExisted[box(row, 0)][val] = true;
        }

        if (size() > 20) {
            quickPermuteRandomly();
        } else {
            slowPermuteRandomly();
        }
        shuffleMap();
        return this;
    }

    @Override
    public int lowestRowOfBox(int box) {
        return getHeightOfBox() * (int) (box / getWith());
    }

    @Override
    public int lowestColOfBox(int box) {
        return getWithOfBox() * (int) (box % getWith());
    }

    @Override
    public String toString() {
        StringBuilder path = new StringBuilder();
        path.append("+");
        for (int i = 0; i < this.getWith(); i++) {
            for (int j = 0; j < this.getWithOfBox(); j++) {
                path.append("----");
            }
            path.append("+");
        }

        StringBuilder s = new StringBuilder();
        s.append(path).append("\r\n");
        for (int i = 0; i < size(); i++) {
            s.append("| ");
            for (int j = 0; j < size(); j++) {
                if (get(i, j) != -1) s.append(String.format("%2d", get(i, j) + 1));
                else s.append("..");
                if (j % getWithOfBox() != getWithOfBox() - 1) s.append("  ");
                else s.append(" | ");
            }
            if ((i + 1) % getHeightOfBox() == 0) s.append("\r\n").append(path);
            s.append("\r\n");
        }
        return s.toString();
    }

    private boolean randomBox(int boxIndex, boolean[][] boxExisted, boolean[][] rowExisted, boolean[][] columnExisted) {
        Random random = new Random();
        for (int row = this.lowestRowOfBox(boxIndex); row < this.getHeightOfBox() + this.lowestRowOfBox(boxIndex); row++) {
            for (int col = this.lowestColOfBox(boxIndex); col < this.getWithOfBox() + this.lowestColOfBox(boxIndex); col++) {
                boolean valid = false;
                for (int val = 0; val < size(); val++) {
                    if (!boxExisted[boxIndex][val] && !rowExisted[row][val]) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) return false;
                int rd = random.nextInt(size());
                while (boxExisted[boxIndex][rd] || rowExisted[row][rd]) {
                    rd = random.nextInt(size());
                }

                this.set(row, col, rd);
                boxExisted[boxIndex][rd] = true;
                columnExisted[col][rd] = true;
                rowExisted[row][rd] = true;
            }
        }
        return true;
    }

    // slower but looks better
    private boolean slowPermuteRandomly() {
        BacktrackingSolution solution = new BacktrackingSolution(this.size());
        solution.randomNewMap(0, 0, this);
        if(solution.numberOfSolutions == 1) {
            this.setMap(solution.solution.getMap());
            return true;
        }

        if(size() > 9) {
            quickPermuteRandomly();
            return true;
        }

        int[] min = new int[0];
        int minRow = -1, minCol = -1;
        boolean containEmpty = false;
        for (int row = 0; row < size(); row++) {
            for (int col = 0; col < size(); col++) {
                if (this.get(row, col) >= 0) continue;
                containEmpty = true;
                int[] tmp = calculateValidNumber(row, col);
                if (tmp.length > min.length) {
                    min = tmp;
                    minRow = row;
                    minCol = col;
                }
            }
        }

        if (min.length == 0) {
            return !containEmpty;
        }
        min = Util.shuffleUp(min);
        for (int val : min) {
            this.set(minRow, minCol, val);
            boolean solved = slowPermuteRandomly();
            if (solved) return true;
            this.set(minRow, minCol, -1);
        }
        return false;
    }

    private int[] calculateValidNumber(int row, int col) {
        boolean[] existed = new boolean[size()];
        for (int r = 0; r < size(); r++) {
            if (r == row) continue;
            if (this.get(r, col) < 0) continue;
            existed[this.get(r, col)] = true;
        }
        for (int c = 0; c < size(); c++) {
            if (c == col) continue;
            if (this.get(row, c) < 0) continue;
            existed[this.get(row, c)] = true;
        }
        for (int _r = this.lowestRowOfBox(this.box(row, col)), r = _r; r < _r + this.getHeightOfBox(); r++) {
            for (int _c = this.lowestColOfBox(this.box(row, col)), c = _c; c < _c + this.getWithOfBox(); c++) {
                if (r == row && c == col) continue;
                if (this.get(r, c) < 0) continue;
                existed[this.get(r, c)] = true;
            }
        }

        int c = 0;
        for (boolean b : existed) if (!b) c++;
        int[] result = new int[c];
        int s = 0;
        for (int i = 0; i < size(); i++) {
            if (!existed[i]) result[s++] = i;
        }
        return result;
    }

    private void quickPermuteRandomly() {
        // random column
        ArrayList<Integer> set = new ArrayList<>();
        for (int i = 0; i < this.getWithOfBox(); i++) set.add(i);
        ArrayList<ArrayList<Integer>> permutation = new ArrayList<>();
        Util.permutation(set, permutation);
        permutation.remove(set);
        for (int col = 0; col < this.getWith(); col++) {
            ArrayList<Integer> ran = Util.randomSet(this.getHeight() - 1, permutation.size());
            for (int row = 1; row < this.getHeight(); row++) {
                ArrayList<Integer> p = permutation.get(ran.get(row - 1));
                for (int i = 0; i < this.getHeightOfBox(); i++) {
                    for (int j = 0; j < this.getWithOfBox(); j++) {
                        this.set(row * this.getHeightOfBox() + i, col * this.getWithOfBox() + j,
                                this.get(i, p.get(j) + col * this.getWithOfBox()));
                    }
                }
            }
        }
    }

    private void shuffleMap(){
        Random random = new Random();
        // step 2: swap rows
        ArrayList<Integer> set = new ArrayList<>();
        for (int i = 0; i < this.getHeightOfBox(); i++) set.add(i);
        ArrayList<ArrayList<Integer>> permutation = new ArrayList<>();
        Util.permutation(set, permutation);
        for (int row = 0; row < this.getHeight(); row++) {
            int[][] temp = new int[this.getHeightOfBox()][this.size()];
            ArrayList<Integer> p = permutation.get(random.nextInt(permutation.size()));
            for (int i = 0; i < this.getHeightOfBox(); i++) {
                for (int j = 0; j < size(); j++) temp[i][j] = this.get(p.get(i) + row * this.getHeightOfBox(), j);
            }
            for (int i = 0; i < this.getHeightOfBox(); i++) {
                for (int j = 0; j < size(); j++) {
                    this.set(i + row * this.getHeightOfBox(), j, temp[i][j]);
                }
            }
        }
        // step 3: swap columns
        set = new ArrayList<>();
        for (int i = 0; i < this.getWithOfBox(); i++) set.add(i);
        permutation = new ArrayList<>();
        Util.permutation(set, permutation);
        for (int col = 0; col < this.getWith(); col++) {
            int[][] temp = new int[this.size()][this.getWithOfBox()];
            ArrayList<Integer> p = permutation.get(random.nextInt(permutation.size()));
            for (int i = 0; i < this.getWithOfBox(); i++) {
                for (int j = 0; j < size(); j++) temp[j][i] = this.get(j, p.get(i) + col * this.getWithOfBox());
            }
            for (int i = 0; i < this.getWithOfBox(); i++) {
                for (int j = 0; j < size(); j++) {
                    this.set(j, i + col * this.getWithOfBox(), temp[j][i]);
                }
            }
        }
    }
}
