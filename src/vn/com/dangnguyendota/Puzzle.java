package vn.com.dangnguyendota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public abstract class Puzzle {
    public static int EmptySquare = -1;

    private int[][] board;
    private int size;
    public int[][] form;

    Puzzle(int size) {
        this.size = size;
        this.board = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(this.board[i], EmptySquare);
        }
    }

    public void setBoard(int[][] board) {
        if (board.length != size) {
            throw new IllegalArgumentException("invalid board size " + size + "x" + size);
        }
        this.board = new int[size][size];
        for(int i = 0; i < size; i++) {
            if (board[i].length != size) {
                throw new IllegalArgumentException("invalid board size " + size + "x" + size);
            }
            for(int j = 0; j < size; j++) {
                if(board[i][j] < 0 || board[i][j] > size) {
                    throw new IllegalArgumentException("invalid board (value: " + board[i][j] + ")");
                }
                this.board[i][j] = board[i][j] - 1;
            }
        }
    }

    public int difficultyScore(Puzzle answer) {
        Puzzle question = copy();
        int score = 0;
        while (!isFull()) {
            int[] t = getMinChoiceSquare();
            int minChoicesOfAllSquares = t[0];
            int minRow = t[1];
            int minCol = t[2];
            if (minChoicesOfAllSquares != Integer.MAX_VALUE) {
                score += (minChoicesOfAllSquares - 1) * (minChoicesOfAllSquares - 1) * 100;
                set(minRow, minCol, answer.get(minRow, minCol));
            }
        }
        this.setPrivateBoard(question.getBoard());
        while (!isFull()) {
            PuzzleSolver puzzleSolver = new PuzzleSolver(this);
            puzzleSolver.solveLikeHuman();
            this.setPrivateBoard(puzzleSolver.getResult().getBoard());
            int[] t = getMinChoiceSquare();
            int minChoicesOfAllSquares = t[0];
            int minRow = t[1];
            int minCol = t[2];
            if (minChoicesOfAllSquares != Integer.MAX_VALUE) {
                score += (minChoicesOfAllSquares - 1) * (minChoicesOfAllSquares - 1) * 1000;
                set(minRow, minCol, answer.get(minRow, minCol));
            }
        }
        this.setPrivateBoard(question.getBoard());
        score += numberOfEmptySquares();
        return score;
    }

    public int getWith() { return 0;};

    public int getHeight() { return 0;};

    public int getWithOfBox() {return  0;};

    public int getHeightOfBox() {return 0;};

    protected int lowestRowOfBox(int box) {
        if(isClassicForm()) {
            return getHeightOfBox() * (box / getWith());
        }
        return 0;
    }

    protected int lowestColOfBox(int box) {
        if(isClassicForm()) {
            return getWithOfBox() * (box % getWith());
        }
        return 0;
    }

    /**
     * the number of box where square(row, column) in
     * @param row row
     * @param col column
     * @return the number of box contains square at (row, column)
     */
    public int boxNumber(int row, int col) {
        if(isClassicForm()) {
            // classic form
            return ((int) (row / getHeightOfBox())) * getWith() + (int) (col / getWithOfBox());
        } else {
            // custom form
            return this.form[row][col];
        }
    }

    /**
     * Generate random completed puzzle
     * @return completed puzzle
     */
    public Puzzle random() {
        if(isClassicForm()) {
            return randomClassicForm();
        } else {
            return randomCustomForm();
        }
    }

    public int[][] getForm() {
        return form;
    }

    public String toString() {
        if(isClassicForm()) {
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
                    if (get(i, j) != EmptySquare) s.append(String.format("%2d", get(i, j) + 1));
                    else s.append("..");
                    if (j % getWithOfBox() != getWithOfBox() - 1) s.append("  ");
                    else s.append(" | ");
                }
                if ((i + 1) % getHeightOfBox() == 0) s.append("\r\n").append(path);
                s.append("\r\n");
            }
            return s.toString();
        } else {
            StringBuilder s = new StringBuilder();
            s.append(getPath(0, 0)).append(Util.newLine);
            for (int i = 0; i < size(); i++) {
                s.append(getRow(i)).append(Util.newLine);
                if (i != size() - 1) s.append(getPath(i, i + 1)).append(Util.newLine);
            }
            s.append(getPath(size() - 1, size() - 1)).append(Util.newLine);
            return s.toString();
        }
    }

    private Puzzle randomClassicForm() {
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
                Puzzle puzzle = this.copy();

                /* random board */
                boolean found = randomBox(i, boxExisted, rowExisted, columnExisted);

                if (found) break;

                /* if random fail, backup data and continue */
                this.setPrivateBoard(puzzle.getBoard());
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
            boxExisted[boxNumber(row, 0)][val] = true;
        }

        if (size() > 20) {
            quickPermuteRandomly();
        } else {
            slowPermuteRandomly();
        }
        shuffleMap();
        return this;
    }

    private Puzzle randomCustomForm() {
        reset();
        BacktrackingSolution solution = new BacktrackingSolution(this.size());
        solution.randomNewMap(0, 0, this);
        if (solution.numberOfSolutions == 1) {
            this.setPrivateBoard(solution.solution.getBoard());
        }
        return this;
    }

    /**
     * @return a copy of this puzzle
     */
    public abstract Puzzle copy();

    // all boxes are square
    public boolean isClassicForm() {
        return form == null;
    }

    /**
     * @return true if this puzzle is solved if not return false
     */
    public boolean isSolved() {
        for(int[] row : board) {
            for (int square : row) {
                if (square == EmptySquare) return false;
            }
        }
        return true;
    }

    public int size() {
        return size;
    }

    public int get(int row, int col) {
        return board[row][col];
    }

    public boolean isEmptySquare(int row, int col) {
        return board[row][col] == EmptySquare;
    }

    public int[][] getBoard() {
        return board;
    }

    void set(int row, int col, int value) {
        this.board[row][col] = value;
    }

    protected void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                set(i, j, EmptySquare);
            }
        }
    }

    boolean isInASameBox(int row1, int col1, int row2, int col2) {
        return boxNumber(row1, col1) == boxNumber(row2, col2);
    }

    private void setPrivateBoard(int[][] board) {
        this.board = new int[size][size];
        for(int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, size);
        }
    }

    private int[] getMinChoiceSquare() {
        int minChoicesOfAllSquares = Integer.MAX_VALUE;
        int minRow = 0, minCol = 0;
        for (int row = 0; row < size(); row++) {
            for (int col = 0; col < size(); col++) {
                if (get(row, col) != EmptySquare) continue;
                boolean[] existed = new boolean[size()];

                for (int r = 0; r < size(); r++) {
                    if (r == row) continue;
                    if (get(r, col) == EmptySquare) continue;
                    existed[get(r, col)] = true;
                }
                for (int c = 0; c < size(); c++) {
                    if (c == col) continue;
                    if (get(row, c) == EmptySquare) continue;
                    existed[get(row, c)] = true;
                }
                for (int r = 0; r < size(); r++) {
                    for (int c = 0; c < size(); c++) {
                        if (!isInASameBox(r, c, row, col)) continue;
                        if (r == row || c == col) continue;
                        if (get(r, c) == EmptySquare) continue;
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
        return new int[]{minChoicesOfAllSquares, minRow, minCol};
    }

    private boolean isFull() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (get(row, col) == EmptySquare) return false;
            }
        }
        return true;
    }

    private int numberOfEmptySquares() {
        int count = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (get(row, col) == EmptySquare) count++;
            }
        }
        return count;
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
            this.setPrivateBoard(solution.solution.getBoard());
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
            this.set(minRow, minCol, EmptySquare);
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
        for (int _r = this.lowestRowOfBox(this.boxNumber(row, col)), r = _r; r < _r + this.getHeightOfBox(); r++) {
            for (int _c = this.lowestColOfBox(this.boxNumber(row, col)), c = _c; c < _c + this.getWithOfBox(); c++) {
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

    private String getPath(int r1, int r2) {
        StringBuilder path = new StringBuilder();
        if (!isInASameBox(r1, 0, r2, 0) || r1 == r2) path.append("+");
        else path.append("|");
        for (int i = 0; i < size() - 1; i++) {
            if(isInASameBox(r1, i, r2, i) && r1 != r2) {
                path.append("   ");
            } else {
                path.append("---");
            }
            if (!isInASameBox(r1, i, r1, i + 1) || !isInASameBox(r2, i, r2, i + 1)) {
                path.append("+");
            } else {
                path.append(" ");
            }
        }

        if(isInASameBox(r1, size() - 1, r2, size() - 1) && r1 != r2) {
            path.append("   ");
        } else {
            path.append("---");
        }
        if (!isInASameBox(r1, size() - 1, r2, size() - 1) || r1 == r2) path.append("+");
        else path.append("|");
        return path.toString();
    }

    private String getRow(int row) {
        StringBuilder path = new StringBuilder();
        path.append("|");
        for (int i = 0; i < size(); i++) {
            path.append(" ").append(getSymbol(row, i)).append(" ");
            if(i == size() - 1) continue;
            if (!isInASameBox(row, i, row, i + 1)) path.append("|");
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
