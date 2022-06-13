package vn.com.dangnguyendota;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Example {

    // generates sudoku puzzles and save to text file.
    static void generateAndSaveToFile() {
        // level of puzzle (sudoku: 16x16)
        int level = 16;
        // the number of puzzles want to generate
        int numberOfPuzzles = 100;
        // the minimum of score
        int minScore = 1;
        // the maximum of score
        int maxScore = 10000000;
        // maximum thinking time (millisecond)
        long time = 60 * 5000;
        // the path where result will be stored
        String path = String.format("example-result/sudoku%dx%d/Gen.txt", level, level);

        // list of sudoku puzzle
        ArrayList<Game> games = new ArrayList<>();
        while (numberOfPuzzles > 0) {
            Game game = Generator.generate(level, time, minScore, maxScore);
            System.out.println(game.getQuestion());
            System.out.println(game.getScore());
            games.add(game);
            numberOfPuzzles--;
            System.out.println(numberOfPuzzles + " number of puzzles left");
        }

        // sort puzzles by score
        games.sort((o1, o2) -> {
            if (o1.getScore() == o2.getScore()) return 0;
            return o1.getScore() > o2.getScore() ? 1 : -1;
        });

        // write result to file
        int index = 1;
        StringBuilder out = new StringBuilder();
        for (Game game : games) {
            out.append(Util.newLine);
            out.append("Puzzle number: ").append(index++);
            out.append(Util.newLine);
            out.append("Difficulty score: ").append(game.getScore());
            out.append(Util.newLine);
            out.append(game.getQuestion());
            out.append(Util.newLine);
            out.append(game.getAnswer());
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), StandardCharsets.UTF_8))) {
            writer.write(out.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void generate() {
//         Game game = Generator.generate(7, 5 * 1000, 10, 1000000, Puzzle7.FORM2.toArray());
//        Game game = Generator.generate(7, 5 * 1000, 10, 1000000);
         Game game = Generator.generate(7, 5 * 1000, 10, 1000000, new int[][]{
                 {0, 0, 1, 1, 1, 1, 2},
                 {0, 0, 0, 1, 1, 1, 2},
                 {3, 0, 0, 4, 4, 2, 2},
                 {3, 3, 4, 4, 4, 2, 2},
                 {3, 3, 4, 4, 6, 6, 2},
                 {3, 5, 5, 5, 6, 6, 6},
                 {3, 5, 5, 5, 5, 6, 6}
         });
        System.out.println("score: " + game.getScore());
        System.out.println("Question");
        System.out.println(game.getQuestion());
        System.out.println("Answer");
        System.out.println(game.getAnswer());
        if (game.getQuestion().isClassicForm()) {
            game.getQuestion().getForm();
        }
    }

    static void solve() {
        Puzzle7 puzzle = new Puzzle7(Puzzle7.FORM2);
        puzzle.setBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 7, 0},
                {0, 2, 7, 0, 0, 4, 0},
                {6, 0, 0, 5, 0, 0, 0},
                {0, 0, 2, 3, 0, 0, 0},
        });

        Puzzle solved = PuzzleSolver.solve(puzzle, 5 * 1000);
        System.out.println(solved);
    }

    public static void main(String... args) {
//        generateAndSaveToFile();
//        generate();
//        solve();
    }
}
