package vn.com.dangnguyendota;

import static vn.com.dangnguyendota.Puzzle.EmptySquare;

public class BacktrackingSolution {
    int numberOfSolutions = 0;
    int counter = 0;
    Puzzle solution;
    private int[] root;
    long start;
    long maxTime = 1000000;
    BacktrackingSolution(){
        start = System.currentTimeMillis();
    }

    BacktrackingSolution(int size){
        root = new int[size];
        for(int i = 0; i < size; i++) root[i] = i;
    }


    boolean checkForOneSolution(Puzzle puzzle) {
        return checkForOneSolution(0, 0, puzzle);
    }

    boolean checkForOneSolution(int r, int c, Puzzle puzzle) {
        counter++;
        if (numberOfSolutions >= 2) {
            return false;
        }
        
        if(System.currentTimeMillis() - start >= maxTime) {
            numberOfSolutions = 100;
            return false;
        }

        if (r >= puzzle.size()) {
            numberOfSolutions++;
            this.solution = puzzle.copy();
            return false;
        }

        if (c >= puzzle.size()) {
            return checkForOneSolution(r + 1, 0, puzzle);
        }

        if (puzzle.get(r, c) != EmptySquare) {
            return checkForOneSolution(r, c + 1, puzzle);
        }

        int num = -1;
        while (true) {
            puzzle.set(r, c, EmptySquare);
            num++;
            if (num >= puzzle.size()) {
                return true;
            }

            if(numberOfSolutions >= 2) return false;

            if (checkSquare(r, c, num, puzzle) && checkRow(r, num, puzzle) && checkColumn(c, num, puzzle)) {
                puzzle.set(r, c, num);
                boolean flag = checkForOneSolution(r, c + 1, puzzle);
                if(!flag) return false;
            }
        }
    }

    boolean randomNewMap(int r, int c, Puzzle puzzle){
        if (numberOfSolutions >= 1) {
            return false;
        }
        counter++;
        if (r >= puzzle.size()) {
            numberOfSolutions++;
            this.solution = puzzle.copy();
            return false;
        }
        if (c >= puzzle.size()) {
            return randomNewMap(r + 1, 0, puzzle);
        }
        if (puzzle.get(r, c) != EmptySquare) {
            return randomNewMap(r, c + 1, puzzle);
        }
        int num = -1;
        int[] shuffle = Util.shuffleUp(root);
        while (true) {
            puzzle.set(r, c, EmptySquare);
            num++;
            if (num >= puzzle.size()) {
                return true;
            }
            if (checkSquare(r, c, shuffle[num], puzzle) && checkRow(r, shuffle[num], puzzle) && checkColumn(c, shuffle[num], puzzle)) {
                puzzle.set(r, c, shuffle[num]);
                boolean flag = randomNewMap(r, c + 1, puzzle);
                if(!flag) return false;
            }
        }
    }

    private boolean checkSquare(int r, int c, int num, Puzzle puzzle) {
        for (int i = 0; i < puzzle.size(); i++) {
            for (int j = 0; j < puzzle.size(); j++) {
                if(!puzzle.isInASameBox(i, j, r, c)) continue;
                if (puzzle.get(i, j) == num)
                    return false;
            }
        }
        return true;
    }

    private boolean checkRow(int r, int num, Puzzle puzzle) {
        for (int value : puzzle.getBoard()[r])
            if (value == num)
                return false;
        return true;
    }

    private boolean checkColumn(int c, int num, Puzzle puzzle) {
        for (int[] row : puzzle.getBoard())
            if (row[c] == num)
                return false;
        return true;
    }
}
