package com.ndn.map;

import com.ndn.Util;

import java.util.ArrayList;
import java.util.Random;

// 4x4, 6x6, 8x8, 9x9, 12x12, 16x16, 25x25
public class MapType1 extends Map {

    MapType1(int size) {
        super(size);
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
        for(int i = 0; i < getWith(); i++) {
            while(true) {
                /* backup */
                boolean[][] _boxExisted = new boolean[getHeight() * getWith()][size()];
                boolean[][] _rowExisted = new boolean[size()][size()];
                boolean[][] _columnExisted = new boolean[size()][size()];
                for(int ix = 0; ix < getHeight() * getWith(); ix++) {
                    if (size() >= 0) System.arraycopy(boxExisted[ix], 0, _boxExisted[ix], 0, size());
                }
                for(int ix = 0; ix < size(); ix++){
                    if (size() >= 0) System.arraycopy(rowExisted[ix], 0, _rowExisted[ix], 0, size());
                    if (size() >= 0) System.arraycopy(columnExisted[ix], 0, _columnExisted[ix], 0, size());
                }
                Map map = this.cloneMap();

                /* random board */
                boolean found = randomBox(i, boxExisted, rowExisted, columnExisted);

                if(found) break;

                /* if random fail, backup data and continue */
                this.setMap(map.getMap());
                boxExisted = _boxExisted;
                rowExisted = _rowExisted;
                columnExisted = _columnExisted;
            }
        }

        permuteRandomly();
        return this;
    }

    @Override
    public int lowestRowOfBox(int box) {
        return getHeightOfBox() * (int) (box / getHeight());
    }

    @Override
    public int lowestColOfBox(int box) {
        return getWithOfBox() * (int) (box % getWith());
    }

    @Override
    public String toString() {
        StringBuilder path = new StringBuilder();
        path.append("+");
        for(int i = 0; i < this.getWith(); i++) {
            for(int j = 0; j < this.getWithOfBox(); j++) {
                path.append("---");
            }
            path.append("+");
        }

        StringBuilder s = new StringBuilder();
        s.append(path).append("\r\n");
        for(int i = 0; i < size(); i++){;
            s.append("| ");
            for(int j = 0; j < size(); j++){
                if(get(i, j) != 0) s.append(get(i, j));
                else s.append(".");
                if(j % getWithOfBox() != getWithOfBox() - 1) s.append("  ");
                else s.append(" | ");
            }
            if((i + 1) % getHeightOfBox() == 0) s.append("\r\n").append(path);
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

                this.set(row, col, rd + 1);
                boxExisted[boxIndex][rd] = true;
                columnExisted[col][rd] = true;
                rowExisted[row][rd] = true;
            }
        }
        return true;
    }

    private void permuteRandomly() {
        Random random = new Random();
        // step 1: random column
        ArrayList<Integer> set = new ArrayList<>();
        for(int i = 0; i < this.getWithOfBox(); i++) set.add(i);
        ArrayList<ArrayList<Integer>> permutation = new ArrayList<>();
        Util.permutation(set, permutation);
        permutation.remove(set);
        for(int col = 0; col < this.getWith(); col++) {
            ArrayList<Integer> ran = Util.randomSet(this.getHeight() - 1, permutation.size());
            for(int row = 1; row < this.getHeight(); row++) {
                ArrayList<Integer> p = permutation.get(ran.get(row - 1));
                for(int i = 0; i < this.getHeightOfBox(); i++) {
                    for(int j = 0; j < this.getWithOfBox(); j++) {
                        this.set(row * this.getHeightOfBox() + i, col * this.getWithOfBox() + j,
                                this.get(i, p.get(j) + col * this.getWithOfBox()));
                    }
                }
            }
        }
        // step 2: swap rows
        set = new ArrayList<>();
        for(int i = 0; i < this.getHeightOfBox(); i++) set.add(i);
        permutation = new ArrayList<>();
        Util.permutation(set, permutation);
        for(int row = 0; row < this.getHeight(); row++) {
            int[][] temp = new int[this.getHeightOfBox()][this.size()];
            ArrayList<Integer> p = permutation.get(random.nextInt(permutation.size()));
            for(int i = 0; i < this.getHeightOfBox(); i++) {
                for(int j = 0; j < size(); j++) temp[i][j] = this.get(p.get(i) + row * this.getHeightOfBox(), j);
            }
            for(int i = 0; i < this.getHeightOfBox(); i++) {
                for(int j = 0; j < size(); j++) {
                    this.set(i + row * this.getHeightOfBox(), j, temp[i][j]);
                }
            }
        }
        // step 3: swap columns
        set = new ArrayList<>();
        for(int i = 0; i < this.getWithOfBox(); i++) set.add(i);
        permutation = new ArrayList<>();
        Util.permutation(set, permutation);
        for(int col = 0; col < this.getWith(); col++) {
            int[][] temp = new int[this.size()][this.getWithOfBox()];
            ArrayList<Integer> p = permutation.get(random.nextInt(permutation.size()));
            for(int i = 0; i < this.getWithOfBox(); i++) {
                for(int j = 0; j < size(); j++) temp[j][i] = this.get(j, p.get(i) + col * this.getWithOfBox());
            }
            for(int i = 0; i < this.getWithOfBox(); i++) {
                for(int j = 0; j < size(); j++) {
                    this.set(j, i + col * this.getWithOfBox(), temp[j][i]);
                }
            }
        }
    }
}
