package vn.com.dangnguyendota;

import java.util.Random;

public class PuzzleFactory {
    public static Puzzle newSolvedPuzzle(int size) {
        Puzzle puzzle;
        int r;
        switch (size) {
            case 4:
                r = (new Random()).nextInt(Puzzle4.FORMS.length + 1);
                if (r == Puzzle4.FORMS.length) {
                    puzzle = new Puzzle4();
                } else {
                    puzzle = new Puzzle4(Puzzle4.FORMS[r]);
                }
                break;
            case 5:
                puzzle = new Puzzle5(Puzzle5.FORMS[(new Random()).nextInt(Puzzle5.FORMS.length)]);
                break;
            case 6:
                r = (new Random()).nextInt(Puzzle6.FORMS.length + 1);
                if (r == Puzzle6.FORMS.length) {
                    puzzle = new Puzzle6();
                } else {
                    puzzle = new Puzzle6(Puzzle6.FORMS[r]);
                }
                break;
            case 7:
                puzzle = new Puzzle7(Puzzle7.FORMS[(new Random()).nextInt(Puzzle7.FORMS.length)]);
                break;
            case 8:
                puzzle = new Puzzle8();
                break;
            case 9:
                puzzle = new Puzzle9();
                break;
            case 12:
                puzzle = new Puzzle12();
                break;
            case 16:
                puzzle = new Puzzle16();
                break;
            case 25:
                puzzle = new Puzzle25();
                break;
            case 3:
                // not supported yet, maybe in the feature!
                return null;
            default:
                // other puzzle type will be supported in the feature
                return null;
        }

        return puzzle.random();
    }
}
