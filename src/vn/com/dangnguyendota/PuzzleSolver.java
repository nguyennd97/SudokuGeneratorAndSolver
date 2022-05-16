package vn.com.dangnguyendota;

import java.util.ArrayList;
import java.util.Arrays;

import static vn.com.dangnguyendota.Puzzle.EmptySquare;

public class PuzzleSolver {
    private boolean reduction = false;
    private boolean error = false;
    private int[][][] pseudo;
    private final int size;
    public Puzzle puzzle;

    public static Puzzle solve(Puzzle puzzle) {
        return solve(puzzle, 60_000);
    }

    public static Puzzle solve(Puzzle puzzle, long maxTime) {
        return solve(puzzle, System.currentTimeMillis(), maxTime);
    }


    static Puzzle solve(Puzzle puzzle, long startTime, long maxTime) {
        PuzzleSolver solver = new PuzzleSolver(puzzle);
        solver.solve(startTime, maxTime);
        return solver.getResult();
    }

    PuzzleSolver(Puzzle puzzle) {
        this.pseudo = new int[puzzle.size()][puzzle.size()][];
        this.size = puzzle.size();
        this.puzzle = puzzle.copy();

        for (int i = 0; i < puzzle.size(); i++) {
            for (int j = 0; j < puzzle.size(); j++) {
                if (puzzle.get(i, j) == EmptySquare) {
                    this.pseudo[i][j] = new int[puzzle.size()];
                    for(int t = 0; t < puzzle.size(); t++) this.pseudo[i][j][t] = t;
                } else {
                    this.pseudo[i][j] = new int[]{puzzle.get(i, j)};
                }
            }
        }
    }

    Puzzle getResult() {
        if (!solved() || isError()) {
            return puzzle;
        }

        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                if(!isDone(row, col)) {
                    puzzle.set(row, col, EmptySquare);
                } else {
                    puzzle.set(row, col, this.pseudo[row][col][0]);
                }
            }
        }
        return puzzle;
    }

    void solve(long startedAt, long maxSolvingTime) {
        solveLikeHuman();
        if (!solved() && !isError()) {
            Puzzle copy = this.puzzle.copy();
            BacktrackingSolution solution = new BacktrackingSolution();
            solution.maxTime = maxSolvingTime;
            solution.start = startedAt;
            boolean solved = solution.checkForOneSolution(copy);
            if(solved && solution.numberOfSolutions == 1) {
                this.puzzle = solution.solution;
                PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle);
                this.pseudo = puzzleSolver.pseudo;
            }
        }
    }

    void solveLikeHuman(){
        reduction = true;
        while (reduction) {
            reduction = false;
            easyReduction();
            basicReduction();
            advanceReduction();
            professionalReduction();
            find();
            if (isError()) break;
        }
    }

    boolean solved() {
        if (isError()) return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!isDone(i, j)) return false;
            }
        }
        return true;
    }

    void catchError() {
        error = true;
    }

    boolean isError() {
        return error;
    }


    private boolean isDone(int row, int column) {
        return this.pseudo[row][column].length == 1;
    }

    private int get(int row, int column, int index) {
        return this.pseudo[row][column][index];
    }

    private int[] get(int row, int column) {
        return this.pseudo[row][column];
    }

    private int length(int row, int column) {
        return this.pseudo[row][column].length;
    }

    private boolean equals(int r1, int c1, int r2, int c2) {
        if (length(r1, c1) != length(r2, c2)) return false;
        for (int index = 0, len = length(r1, c1); index < len; index++) {
            if (get(r1, c1, index) != get(r2, c2, index)) return false;
        }
        return true;
    }

    private boolean contain(int row, int column, int value) {
        for (int p : this.pseudo[row][column]) {
            if (p == value) return true;
        }
        return false;
    }

    private boolean isPartOf(int rootRow, int rooCol, int childRow, int childCol) {
        if (length(childRow, childCol) > length(rootRow, rooCol)) return false;
        for (int c : this.pseudo[childRow][childCol]) {
            if (!contain(rootRow, rooCol, c)) return false;
        }
        return true;
    }

    private void change(int row, int column, ArrayList<Integer> buffer) {
        this.pseudo[row][column] = new int[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            this.pseudo[row][column][i] = buffer.get(i);
        }
    }

    private void deleteExisted(int row, int column, boolean[] existed) {
        if (this.isDone(row, column)) return;
        ArrayList<Integer> buffer = new ArrayList<>();
        for (int p : this.pseudo[row][column]) {
            if (existed[p]) {
                reduction = true;
                continue;
            }
            buffer.add(p);
        }
        this.change(row, column, buffer);
    }

    private void delete(int row, int column, int value) {
        boolean found = false;
        int[] buffer = new int[this.pseudo[row][column].length - 1];
        int count = 0;
        for (int i = 0; i < this.pseudo[row][column].length; i++) {
            if (this.pseudo[row][column][i] == value) {
                found = true;
                continue;
            }
            buffer[count] = this.pseudo[row][column][i];
            count++;
        }
        if (!found) throw new IllegalArgumentException("Could not find value in pseudo board to delete.");
        this.pseudo[row][column] = buffer;
    }

    PuzzleSolver copy() {
        return new PuzzleSolver(this.puzzle.copy());
    }

    private void easyReduction() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (!isDone(row, column)) continue;
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
                for (int r = 0; r < size; r++) {
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        if (r == row && c == column) continue;
                        deleteExisted(r, c, existed);
                    }
                }
            }
        }
    }

    private void basicReduction() {
        //row
        boolean[][] checkedRows = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if(checkedRows[row][puzzle.boxNumber(row, column)]) continue;
                checkedRows[row][puzzle.boxNumber(row, column)] = true;
                //Lưu các số có thể có ở các ô cùng hàng khác hộp.
                boolean[] existed = new boolean[size];
                Arrays.fill(existed, true);

                for (int c = 0; c < size; c++) {
                    if (puzzle.boxNumber(row, column) == puzzle.boxNumber(row, c)) continue;
                    for (int p : this.pseudo[row][c]) existed[p] = false;
                }

                //Duyệt qua các ô cùng hộp khác hàng.
                for (int r = 0; r < size; r++) {
                    if (r == row) continue;
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        deleteExisted(r, c, existed);
                    }
                }
                //Lưu các số có thể có ở các ô cùng hộp khác hàng
                Arrays.fill(existed, true);

                for (int r = 0; r < size; r++) {
                    if (r == row) continue;
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        for (int p : this.pseudo[r][c]) existed[p] = false;
                    }
                }

                //Duyệt qua các ô cùng hàng khác hộp.
                for (int c = 0; c < size; c++) {
                    if (puzzle.boxNumber(row, column) == puzzle.boxNumber(row, c)) continue;
                    deleteExisted(row, c, existed);
                }
            }
        }

        boolean[][] checkedColumns = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if(checkedColumns[puzzle.boxNumber(row, column)][column]) continue;
                checkedColumns[puzzle.boxNumber(row, column)][column] = true;
                //Lưu các số có thể có ở các ô cùng cột khác hộp.
                boolean[] colExisted = new boolean[size];
                Arrays.fill(colExisted, true);

                for (int r = 0; r < size; r++) {
                    if (puzzle.boxNumber(row, column) == puzzle.boxNumber(r, column)) continue;
                    for (int p : this.pseudo[r][column]) colExisted[p] = false;
                }

                //Duyệt qua các ô cùng hộp khác cột.
                for (int r = 0; r < size; r++) {
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        if (c == column) continue;
                        deleteExisted(r, c, colExisted);
                    }
                }
                //Lưu các số có thể có ở các ô cùng hộp khác cột
                boolean[] boxExisted = new boolean[size];
                Arrays.fill(boxExisted, true);

                for (int r = 0; r < size; r++) {
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        if (c == column) continue;
                        for (int p : this.pseudo[r][c]) boxExisted[p] = false;
                    }
                }

                //Duyệt qua các ô cùng cột khác hộp.
                for (int r = 0; r < size; r++) {
                    if (puzzle.boxNumber(row, column) == puzzle.boxNumber(r, column)) continue;
                    deleteExisted(r, column, boxExisted);
                }
            }
        }
    }

    private void advanceReduction() {
        int count;
        boolean[] existed = new boolean[size];
        boolean[][] checkedRows = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if(checkedRows[row][puzzle.boxNumber(row, column)]) continue;
                checkedRows[row][puzzle.boxNumber(row, column)] = true;
                //row with box
                Arrays.fill(existed, true);
                count = 0;
                for (int c = 0; c < size; c++) {
                    if (puzzle.boxNumber(row, column) == puzzle.boxNumber(row, c)) continue;
                    if (isDone(row, c)) {
                        existed[this.pseudo[row][c][0]] = false;
                        count++;
                    }
                }

                ArrayList<int[]> notDone = new ArrayList<>();
                for (int r = 0; r < size; r++) {
                    if (r == row) continue;
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        if (isDone(r, c) && !existed[this.pseudo[r][c][0]]) {
                            existed[this.pseudo[r][c][0]] = true;
                            count--;
                        }
                        if (!isDone(r, c)) notDone.add(new int[]{r, c});
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
                for (int r = 0; r < size; r++) {
                    if (r == row) continue;
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        if (isDone(r, c)) {
                            existed[this.pseudo[r][c][0]] = false;
                            count++;
                        }
                    }
                }

                notDone = new ArrayList<>();
                for (int c = 0; c < size; c++) {
                    if (puzzle.boxNumber(row, column) == puzzle.boxNumber(row, c)) continue;
                    if (isDone(row, c) && !existed[this.pseudo[row][c][0]]) {
                        existed[this.pseudo[row][c][0]] = true;
                        count--;
                    }
                    if (!isDone(row, c)) notDone.add(new int[]{row, c});
                }

                if (count == notDone.size() && column > 0) {
                    for (int[] position : notDone) {
                        deleteExisted(position[0], position[1], existed);
                    }
                }
            }
        }

        boolean[][] checkedColumns = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if(checkedColumns[puzzle.boxNumber(row, column)][column]) continue;
                checkedColumns[puzzle.boxNumber(row, column)][column] = true;
                //row with box
                Arrays.fill(existed, true);
                count = 0;
                for (int r = 0; r < size; r++) {
                    if (puzzle.boxNumber(row, column) == puzzle.boxNumber(r, column)) continue;
                    if (isDone(r, column)) {
                        existed[this.pseudo[r][column][0]] = false;
                        count++;
                    }
                }

                ArrayList<int[]> notDone = new ArrayList<>();
                for (int c = 0; c < size; c++) {
                    if (c == column) continue;
                    for (int r = 0; r < size; r++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        if (isDone(r, c) && !existed[this.pseudo[r][c][0]]) {
                            existed[this.pseudo[r][c][0]] = true;
                            count--;
                        }
                        if (!isDone(r, c)) notDone.add(new int[]{r, c});
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
                for (int c = 0; c < size; c++) {
                    if (c == column) continue;
                    for (int r = 0; r < size; r++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        if (isDone(r, c)) {
                            existed[this.pseudo[r][c][0]] = false;
                            count++;
                        }
                    }
                }

                notDone = new ArrayList<>();
                for (int r = 0; r < size; r++) {
                    if (puzzle.boxNumber(row, column) == puzzle.boxNumber(r, column)) continue;
                    if (isDone(r, column) && !existed[this.pseudo[r][column][0]]) {
                        existed[this.pseudo[r][column][0]] = true;
                        count--;
                    }
                    if (!isDone(r, column)) notDone.add(new int[]{r, column});
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
                if (isDone(row, column)) continue;

                boolean[] equal = new boolean[size];
                boolean[] existed = new boolean[size];
                for (int p : this.pseudo[row][column]) existed[p] = true;
                int count = 1;
                //row
                for (int c = 0; c < size; c++) {
                    if (c == column || isDone(row, c)) continue;
                    if (isPartOf(row, column, row, c)) {
                        equal[c] = true;
                        count++;
                    }
                }
                if (count == length(row, column)) {
                    for (int c = 0; c < size; c++) {
                        if (c == column || equal[c] || isDone(row, c)) continue;
                        deleteExisted(row, c, existed);
                    }
                }
                //col
                Arrays.fill(equal, false);
                count = 1;
                for (int r = 0; r < size; r++) {
                    if (r == row || isDone(r, column)) continue;
                    if (isPartOf(row, column, r, column)) {
                        equal[r] = true;
                        count++;
                    }
                }
                if (count == length(row, column)) {
                    for (int r = 0; r < size; r++) {
                        if (r == row || equal[r] || isDone(r, column)) continue;
                        deleteExisted(r, column, existed);
                    }
                }
//                box
                Arrays.fill(equal, false);
                count = 1;
                int index = -1;
                for (int r = 0; r < size; r++) {
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        index++;
                        if (r == row && c == column) continue;
                        if (isDone(r, c)) continue;
                        if (isPartOf(row, column, r, c)) {
                            equal[index] = true;
                            count++;
                        }
                    }
                }
                index = -1;
                if (count == length(row, column)) {
                    for (int r = 0; r < size; r++) {
                        for (int c = 0; c < size; c++) {
                            if(!puzzle.isInASameBox(row, column, r, c)) continue;
                            index++;
                            if (r == row && c == column) continue;
                            if (isDone(r, c) || equal[index]) continue;
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
                    for (int p : this.pseudo[r][column]) {
                        if (!existed[p]) {
                            count--;
                            existed[p] = true;
                        }
                    }
                }
                if (count > 1) {
                    catchError();
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
                    for (int p : this.pseudo[row][c]) {
                        if (!existed[p]) {
                            count--;
                            existed[p] = true;
                        }
                    }
                }
                if (count > 1) {
                    catchError();
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
                for (int r = 0; r < size; r++) {
                    for (int c = 0; c < size; c++) {
                        if(!puzzle.isInASameBox(row, column, r, c)) continue;
                        if (r == row && c == column) continue;
                        for (int p : this.pseudo[r][c]) {
                            if (!existed[p]) {
                                count--;
                                existed[p] = true;
                            }
                        }
                    }
                }
                if (count > 1) {
//                    throw new SudokuException("1 ô không thể có 2 giá trị. Hàm find trong PseudoMap.<box>");
                    catchError();
                    break Loop;
                }
                if (count == 1) {
                    deleteExisted(row, column, existed);
                }
            }
        }
    }

//    public String toString() {
//        int max = 0;
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                max = Math.max(max, this.pseudo[i][j].length);
//            }
//        }
//
//        StringBuilder st = new StringBuilder();
//        StringBuilder line = new StringBuilder();
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < max; j++) {
//                line.append("--");
//            }
//            line.append("--");
//        }
//        for (int i = 0; i < size; i++) {
//            if (i % puzzle.getHeightOfBox() == 0) st.append(line);
//            st.append("\n");
//            st.append("| ");
//            for (int j = 0; j < size; j++) {
//                for (int t = 0; t < max; t++) {
//                    if (t < this.pseudo[i][j].length) st.append(this.pseudo[i][j][t] + 1).append(" ");
//                    else st.append("  ");
//                }
//                if ((j + 1) % puzzle.getWithOfBox() == 0) st.append("| ");
//                else st.append(". ");
//            }
//            st.append("\n");
//        }
//        st.append(line).append("\n");
//        return st.toString();
//    }
}
