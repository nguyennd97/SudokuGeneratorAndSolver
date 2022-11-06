package vn.com.orismaster;

import java.util.Random;

public class PuzzleFactory {
    public static Puzzle newSolvedPuzzle(int size, int[][] form) {
        Puzzle puzzle;
        int r;
        switch (size) {
            case 4:
                if (form == null) {
                    r = (new Random()).nextInt(Puzzle4.FORMS.length + 1);
                    if (r == Puzzle4.FORMS.length) {
                        puzzle = new Puzzle4();
                    } else {
                        puzzle = new Puzzle4(Puzzle4.FORMS[r]);
                    }
                } else {
                    puzzle = new Puzzle4(new Puzzle4.Form(form));
                }

                break;
            case 5:
                puzzle = form == null ? new Puzzle5(Puzzle5.FORMS[(new Random()).nextInt(Puzzle5.FORMS.length)]) : new Puzzle5(new Puzzle5.Form(form));
                break;
            case 6:
                if (form == null) {
                    r = (new Random()).nextInt(Puzzle6.FORMS.length + 1);
                    if (r == Puzzle6.FORMS.length) {
                        puzzle = new Puzzle6();
                    } else {
                        puzzle = new Puzzle6(Puzzle6.FORMS[r]);
                    }
                } else {
                    puzzle = new Puzzle6(new Puzzle6.Form(form));
                }

                break;
            case 7:
                puzzle = form == null ? new Puzzle7(Puzzle7.FORMS[(new Random()).nextInt(Puzzle7.FORMS.length)]) : new Puzzle7(new Puzzle7.Form(form));
                break;
            case 8:
                if (form == null) {
                    r = (new Random()).nextInt(Puzzle8.FORMS.length + 1);
                    if (r == Puzzle8.FORMS.length) {
                        puzzle = new Puzzle8();
                    } else {
                        puzzle = new Puzzle8(Puzzle8.FORMS[r]);
                    }
                } else {
                    puzzle = new Puzzle8(new Puzzle8.Form(form));
                }
                break;
            case 9:
                if (form == null) {
                    r = (new Random()).nextInt(Puzzle9.FORMS.length + 1);
                    if (r == Puzzle9.FORMS.length) {
                        puzzle = new Puzzle9();
                    } else {
                        puzzle = new Puzzle9(Puzzle9.FORMS[r]);
                    }
                } else {
                    puzzle = new Puzzle9(new Puzzle9.Form(form));
                }
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
