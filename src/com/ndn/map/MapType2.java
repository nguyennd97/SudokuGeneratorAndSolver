package com.ndn.map;

import com.ndn.PseudoMap;
import com.ndn.Util;

// 5x5, 7x7
public class MapType2 extends Map {
    public int[][] boxes;

    public MapType2(int size) {
        super(size);
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
        if (solution.numberOfSolutions == 1) {
            this.setMap(solution.solution.getMap());
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getPath(0, 0)).append(Util.newLine);
        for (int i = 0; i < size(); i++) {
            s.append(getRow(i)).append(Util.newLine);
            if (i != size() - 1) s.append(getPath(i, i + 1)).append(Util.newLine);
        }
        s.append(getPath(size() - 1, size() - 1)).append(Util.newLine);
        return s.toString();
    }

    private String getPath(int r1, int r2) {
        StringBuilder path = new StringBuilder();
        if (!sameBox(r1, 0, r2, 0) || r1 == r2) path.append("+");
        else path.append("|");
        for (int i = 0; i < size() - 1; i++) {
            if(sameBox(r1, i, r2, i) && r1 != r2) {
                path.append("   ");
            } else {
                path.append("---");
            }
            if (!sameBox(r1, i, r1, i + 1) || !sameBox(r2, i, r2, i + 1)) {
                path.append("+");
            } else {
                path.append(" ");
            }
        }

        if(sameBox(r1, size() - 1, r2, size() - 1) && r1 != r2) {
            path.append("   ");
        } else {
            path.append("---");
        }
        if (!sameBox(r1, size() - 1, r2, size() - 1) || r1 == r2) path.append("+");
        else path.append("|");
        return path.toString();
    }

    private String getRow(int row) {
        StringBuilder path = new StringBuilder();
        path.append("|");
        for (int i = 0; i < size(); i++) {
            path.append(" ").append(getSymbol(row, i)).append(" ");
            if(i == size() - 1) continue;
            if (!sameBox(row, i, row, i + 1)) path.append("|");
            else path.append(" ");
        }
        path.append("|");
        return path.toString();
    }

    private String getSymbol(int row, int col) {
        int n = get(row, col) + 1;
        if (n == 0) return ".";
        return String.valueOf(n);
    }
}
