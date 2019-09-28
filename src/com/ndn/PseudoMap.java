package com.ndn;

import com.ndn.map.BacktrackingSolution;
import com.ndn.map.Map;

import java.util.ArrayList;
import java.util.Arrays;

public class PseudoMap {
    private boolean foundReduction = false;
    private boolean findError = false;
    private int[][][] pseudo;
    private int size;
    public Map map;

    PseudoMap(Map map) {
        pseudo = new int[map.size()][map.size()][];
        size = map.size();
        this.map = map;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                if (map.get(i, j) == -1) {
                    pseudo[i][j] = new int[map.size()];
                    for(int t = 0; t < map.size(); t++) pseudo[i][j][t] = t;
                } else {
                    pseudo[i][j] = new int[]{map.get(i, j)};
                }
            }
        }
    }

    Map getResult() {
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                if(!done(row, col)) {
                    map.set(row, col, -1);
                } else {
                    map.set(row, col, pseudo[row][col][0]);
                }
            }
        }
        return map;
    }

    void solve() {
        foundReduction = true;
        while (foundReduction) {
            foundReduction = false;
            easyReduction();
            basicReduction();
            advanceReduction();
            professionalReduction();
            find();
            if (errorFound()) break;
        }

        if (!solved() && !errorFound()) {
            Map copy = this.map.cloneMap();
            BacktrackingSolution solution = new BacktrackingSolution();
            boolean solved = solution.checkForOneSolution(copy);
            if(solved && solution.numberOfSolutions == 1) {
                this.map = solution.solution;
                PseudoMap pseudoMap = new PseudoMap(map);
                this.pseudo = pseudoMap.pseudo;
            }
        }
    }

    boolean solved() {
        if (errorFound()) return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!done(i, j)) return false;
            }
        }
        return true;
    }

    void sendError() {
        findError = true;
    }

    boolean errorFound() {
        return findError;
    }


    boolean done(int row, int column) {
        return pseudo[row][column].length == 1;
    }

    int get(int row, int column, int index) {
        return pseudo[row][column][index];
    }

    private int[] get(int row, int column) {
        return pseudo[row][column];
    }

    private int length(int row, int column) {
        return pseudo[row][column].length;
    }

    private boolean equals(int r1, int c1, int r2, int c2) {
        if (length(r1, c1) != length(r2, c2)) return false;
        for (int index = 0, len = length(r1, c1); index < len; index++) {
            if (get(r1, c1, index) != get(r2, c2, index)) return false;
        }
        return true;
    }

    private boolean contain(int row, int column, int value) {
        for (int p : pseudo[row][column]) {
            if (p == value) return true;
        }
        return false;
    }

    private boolean isPartOf(int rootRow, int rooCol, int childRow, int childCol) {
        if (length(childRow, childCol) > length(rootRow, rooCol)) return false;
        for (int c : pseudo[childRow][childCol]) {
            if (!contain(rootRow, rooCol, c)) return false;
        }
        return true;
    }

    private void change(int row, int column, ArrayList<Integer> buffer) {
        pseudo[row][column] = new int[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            pseudo[row][column][i] = buffer.get(i);
        }
    }

    private void deleteExisted(int row, int column, boolean[] existed) {
        if (this.done(row, column)) return;
        ArrayList<Integer> buffer = new ArrayList<>();
        for (int p : pseudo[row][column]) {
            if (existed[p]) {
                foundReduction = true;
                continue;
            }
            buffer.add(p);
        }
        this.change(row, column, buffer);
    }

    private void delete(int row, int column, int value) {
        boolean found = false;
        int[] buffer = new int[pseudo[row][column].length - 1];
        int count = 0;
        for (int i = 0; i < pseudo[row][column].length; i++) {
            if (pseudo[row][column][i] == value) {
                found = true;
                continue;
            }
            buffer[count] = pseudo[row][column][i];
            count++;
        }
        if (!found) throw new IllegalArgumentException("Could not find value in pseudo board to delete.");
        pseudo[row][column] = buffer;
    }

    PseudoMap copy() {
        return new PseudoMap(this.map.cloneMap());
    }

    private void easyReduction() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (!done(row, column)) continue;
                boolean[] existed = new boolean[size];
                existed[get(row, column, 0)] = true;
                for (int c = 0; c < size; c++) {
                    if (c == column) continue;
                    deleteExisted(row, c, existed);
                }
                for (int r = 0; r < size; r++) {
                    if (r == row) continue;
                    deleteExisted(r, column, existed);
                }
                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        if (r == row && c == column) continue;
                        deleteExisted(r, c, existed);
                    }
                }
            }
        }
    }

    private void basicReduction() {
        //row
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column += map.getWithOfBox()) {
                //Lưu các số có thể có ở các ô cùng hàng khác hộp.
                boolean[] existed = new boolean[size];
                Arrays.fill(existed, true);

                for (int c = 0; c < size; c++) {
                    if (map.box(row, column) == map.box(row, c)) continue;
                    for (int p : pseudo[row][c]) existed[p] = false;
                }

                //Duyệt qua các ô cùng hộp khác hàng.
                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    if (r == row) continue;
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        deleteExisted(r, c, existed);
                    }
                }
                //Lưu các số có thể có ở các ô cùng hộp khác hàng
                Arrays.fill(existed, true);

                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    if (r == row) continue;
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        for (int p : pseudo[r][c]) existed[p] = false;
                    }
                }

                //Duyệt qua các ô cùng hàng khác hộp.
                for (int c = 0; c < size; c++) {
                    if (map.box(row, column) == map.box(row, c)) continue;
                    deleteExisted(row, c, existed);
                }
            }
        }

        for (int row = 0; row < size; row += map.getHeightOfBox()) {
            for (int column = 0; column < size; column++) {
                //Lưu các số có thể có ở các ô cùng cột khác hộp.
                boolean[] colExisted = new boolean[size];
                Arrays.fill(colExisted, true);

                for (int r = 0; r < size; r++) {
                    if (map.box(row, column) == map.box(r, column)) continue;
                    for (int p : pseudo[r][column]) colExisted[p] = false;
                }

                //Duyệt qua các ô cùng hộp khác cột.
                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        if (c == column) continue;
                        deleteExisted(r, c, colExisted);
                    }
                }
                //Lưu các số có thể có ở các ô cùng hộp khác cột
                boolean[] boxExisted = new boolean[size];
                Arrays.fill(boxExisted, true);

                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        if (c == column) continue;
                        for (int p : pseudo[r][c]) boxExisted[p] = false;
                    }
                }

                //Duyệt qua các ô cùng cột khác hộp.
                for (int r = 0; r < size; r++) {
                    if (map.box(row, column) == map.box(r, column)) continue;
                    deleteExisted(r, column, boxExisted);
                }
            }
        }
    }

    private void advanceReduction() {
        int count;
        boolean[] existed = new boolean[size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column += map.getWithOfBox()) {
                //row with box
                Arrays.fill(existed, true);
                count = 0;
                for (int c = 0; c < size; c++) {
                    if (map.box(row, column) == map.box(row, c)) continue;
                    if (done(row, c)) {
                        existed[pseudo[row][c][0]] = false;
                        count++;
                    }
                }

                ArrayList<int[]> notDone = new ArrayList<>();
                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    if (r == row) continue;
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        if (done(r, c) && !existed[pseudo[r][c][0]]) {
                            existed[pseudo[r][c][0]] = true;
                            count--;
                        }
                        if (!done(r, c)) notDone.add(new int[]{r, c});
                    }
                }

                if (count == notDone.size() && count > 0) {
                    for (int[] position : notDone) {
                        deleteExisted(position[0], position[1], existed);
                    }
                }

                //box with row
                Arrays.fill(existed, true);
                count = 0;
                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    if (r == row) continue;
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        if (done(r, c)) {
                            existed[pseudo[r][c][0]] = false;
                            count++;
                        }
                    }
                }

                notDone = new ArrayList<>();
                for (int c = 0; c < size; c++) {
                    if (map.box(row, column) == map.box(row, c)) continue;
                    if (done(row, c) && !existed[pseudo[row][c][0]]) {
                        existed[pseudo[row][c][0]] = true;
                        count--;
                    }
                    if (!done(row, c)) notDone.add(new int[]{row, c});
                }

                if (count == notDone.size() && column > 0) {
                    for (int[] position : notDone) {
                        deleteExisted(position[0], position[1], existed);
                    }
                }
            }
        }

        for (int row = 0; row < size; row += map.getHeightOfBox()) {
            for (int column = 0; column < size; column++) {
                //row with box
                Arrays.fill(existed, true);
                count = 0;
                for (int r = 0; r < size; r++) {
                    if (map.box(row, column) == map.box(r, column)) continue;
                    if (done(r, column)) {
                        existed[pseudo[r][column][0]] = false;
                        count++;
                    }
                }

                ArrayList<int[]> notDone = new ArrayList<>();
                for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                    if (c == column) continue;
                    for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                        if (done(r, c) && !existed[pseudo[r][c][0]]) {
                            existed[pseudo[r][c][0]] = true;
                            count--;
                        }
                        if (!done(r, c)) notDone.add(new int[]{r, c});
                    }
                }

                if (count == notDone.size() && count > 0) {
                    for (int[] position : notDone) {
                        deleteExisted(position[0], position[1], existed);
                    }
                }

                //box with row
                Arrays.fill(existed, true);
                count = 0;
                for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                    if (c == column) continue;
                    for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                        if (done(r, c)) {
                            existed[pseudo[r][c][0]] = false;
                            count++;
                        }
                    }
                }

                notDone = new ArrayList<>();
                for (int r = 0; r < size; r++) {
                    if (map.box(row, column) == map.box(r, column)) continue;
                    if (done(r, column) && !existed[pseudo[r][column][0]]) {
                        existed[pseudo[r][column][0]] = true;
                        count--;
                    }
                    if (!done(r, column)) notDone.add(new int[]{r, column});
                }

                if (count == notDone.size() && count > 0) {
                    for (int[] position : notDone) {
                        deleteExisted(position[0], position[1], existed);
                    }
                }
            }
        }
    }

    private void professionalReduction() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (done(row, column)) continue;

                boolean[] equal = new boolean[size];
                boolean[] existed = new boolean[size];
                for (int p : pseudo[row][column]) existed[p] = true;
                int count = 1;
                //row
                for (int c = 0; c < size; c++) {
                    if (c == column || done(row, c)) continue;
                    if (isPartOf(row, column, row, c)) {
                        equal[c] = true;
                        count++;
                    }
                }
                if (count == length(row, column)) {
                    for (int c = 0; c < size; c++) {
                        if (c == column || equal[c] || done(row, c)) continue;
                        deleteExisted(row, c, existed);
                    }
                }
                //col
                Arrays.fill(equal, false);
                count = 1;
                for (int r = 0; r < size; r++) {
                    if (r == row || done(r, column)) continue;
                    if (isPartOf(row, column, r, column)) {
                        equal[r] = true;
                        count++;
                    }
                }
                if (count == length(row, column)) {
                    for (int r = 0; r < size; r++) {
                        if (r == row || equal[r] || done(r, column)) continue;
                        deleteExisted(r, column, existed);
                    }
                }
//                box
                Arrays.fill(equal, false);
                count = 1;
                int index = -1;
                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        index++;
                        if (r == row && c == column) continue;
                        if (done(r, c)) continue;
                        if (isPartOf(row, column, r, c)) {
                            equal[index] = true;
                            count++;
                        }
                    }
                }
                index = -1;
                if (count == length(row, column)) {
                    for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                        for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column); c++) {
                            index++;
                            if (r == row && c == column) continue;
                            if (done(r, c) || equal[index]) continue;
                            deleteExisted(r, c, existed);
                        }
                    }
                }
            }
        }
    }

    private void find() {
        Loop:
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                boolean[] existed = new boolean[size];
                int count = size;
                //row
                for (int r = 0; r < size; r++) {
                    if (r == row) continue;
                    for (int p : pseudo[r][column]) {
                        if (!existed[p]) {
                            count--;
                            existed[p] = true;
                        }
                    }
                }
                if (count > 1) {
                    sendError();
                    break Loop;
                }
                if (count == 1) {
                    deleteExisted(row, column, existed);
                    continue;
                }
                //column
                Arrays.fill(existed, false);
                count = size;
                for (int c = 0; c < size; c++) {
                    if (c == column) continue;
                    for (int p : pseudo[row][c]) {
                        if (!existed[p]) {
                            count--;
                            existed[p] = true;
                        }
                    }
                }
                if (count > 1) {
                    sendError();
                    break Loop;
                    //throw new SudokuException("1 ô không thể có 2 giá trị. Hàm find trong PseudoMap.<column>");
                }
                if (count == 1) {
                    deleteExisted(row, column, existed);
                    continue;
                }

                //box
                Arrays.fill(existed, false);
                count = size;
                for (int r = map.rowOfBoxesContainRow(row); r < map.rowOfBoxesContainRow(row) + map.getHeightOfBox(); r++) {
                    for (int c = map.colOfBoxesContainCol(column); c < map.colOfBoxesContainCol(column) + map.getWithOfBox(); c++) {
                        if (r == row && c == column) continue;
                        for (int p : pseudo[r][c]) {
                            if (!existed[p]) {
                                count--;
                                existed[p] = true;
                            }
                        }
                    }
                }
                if (count > 1) {
//                    throw new SudokuException("1 ô không thể có 2 giá trị. Hàm find trong PseudoMap.<box>");
                    sendError();
                    break Loop;
                }
                if (count == 1) {
                    deleteExisted(row, column, existed);
                }
            }
        }
    }

    public String toString() {
        int max = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                max = Math.max(max, pseudo[i][j].length);
            }
        }

        StringBuilder st = new StringBuilder();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < max; j++) {
                line.append("--");
            }
            line.append("--");
        }
        for (int i = 0; i < size; i++) {
            if (i % map.getHeightOfBox() == 0) st.append(line);
            st.append("\n");
            st.append("| ");
            for (int j = 0; j < size; j++) {
                for (int t = 0; t < max; t++) {
                    if (t < pseudo[i][j].length) st.append(pseudo[i][j][t] + 1).append(" ");
                    else st.append("  ");
                }
                if ((j + 1) % map.getWithOfBox() == 0) st.append("| ");
                else st.append(". ");
            }
            st.append("\n");
        }
        st.append(line).append("\n");
        return st.toString();
    }
}
