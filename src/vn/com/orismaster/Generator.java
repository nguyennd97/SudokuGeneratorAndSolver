package vn.com.orismaster;

import java.util.ArrayList;
import java.util.Random;

import static vn.com.orismaster.Puzzle.EmptySquare;

public class Generator {
    private static final Random random = new Random();

    public static Game generate(int size, long time, int minScore, int maxScore) {
        return generate(size, time, minScore, maxScore, null);
    }

    public static Game generate(int size, long time, int maxScore) {
        return generate(size, time, maxScore, null);
    }


    /**
     * generate random game contain solved puzzle, puzzle and score of puzzle
     *
     * @param size     the type of puzzle (example: puzzle 9x9 size = 9, 7x7 size = 7)
     * @param time     maximum thinking time (millisecond)
     * @param minScore the minimum of puzzle score
     * @param maxScore the maximum of puzzle score
     * @param form     the form of puzzle, set null if using a random registered form
     * @return Game
     */
    public static Game generate(int size, long time, int minScore, int maxScore, int[][] form) {
        if (minScore > maxScore) throw new IllegalArgumentException("Min score is bigger than max score");
        long from = System.currentTimeMillis();

        while (true) {
            Game game = Generator.generate(size, time, maxScore, form);

            // only accept puzzle with score between minScore and maxScore
            // if score is not as expected, try again
            if (game.getScore() >= minScore && game.getScore() <= maxScore) {
                return game;
            }

            if (System.currentTimeMillis() - from > time) {
                return game;
            }
        }
    }

    /**
     * generate random game contain solved puzzle, puzzle and score of puzzle
     *
     * @param size     the type of puzzle (example: puzzle 9x9 size = 9, 7x7 size = 7)
     * @param time     maximum thinking time (millisecond)
     * @param maxScore the maximum of puzzle score
     * @param form     the form of puzzle, set null if using a random registered form
     * @return Game
     */
    public static Game generate(int size, long time, int maxScore, int[][] form) {
        // random answer first
        Puzzle answer = PuzzleFactory.newSolvedPuzzle(size, form);
        if (answer == null) {
            throw new IllegalArgumentException("invalid input");
        }

        // copy answer to question
        Puzzle question = answer.copy();

        // remove squares in question until question is valid

        // {removed} tracks which square is removed or tried to remove from question
        boolean[][] removed = new boolean[question.size()][question.size()];

        // the loop will be broke if number of removed (or tried to remove) squares = total number of all squares in the board
        int numberOfRemovedSquares = 0;

        // the loop will be broke if current time - start time > maximum thinking time
        long start_time = System.currentTimeMillis();
        while (System.currentTimeMillis() - start_time < time) {
            if (numberOfRemovedSquares == question.size() * question.size()) break;

            // get the square not removed
            int row = 0, col = 0;
            do {
                row = random.nextInt(question.size());
                col = random.nextInt(question.size());
            } while (question.get(row, col) == EmptySquare || removed[row][col]);
            removed[row][col] = true;
            numberOfRemovedSquares++;

            int value = question.get(row, col);

            // remove value at square (row, col)
            question.set(row, col, EmptySquare);

            // solve puzzle, if puzzle can not be solved => set question back to old question
            Puzzle puzzle = PuzzleSolver.solve(question, start_time, time);
            if (!puzzle.isSolved()) {
                question.set(row, col, value);
            }

            // if current score is greater than old score
            // set question back to old question and break the loop
            int score = question.difficultyScore(answer);
            if (score > maxScore) {
                question.set(row, col, value);
                break;
            }
        }

        return new Game(question, answer);
    }
}
