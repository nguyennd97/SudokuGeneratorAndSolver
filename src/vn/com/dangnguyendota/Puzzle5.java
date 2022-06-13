package vn.com.dangnguyendota;

import java.util.Arrays;
import java.util.List;

public class Puzzle5 extends Puzzle {
    public static class Form {
        private int[][] form;
        public Form(int[][] form) {
            this.form = form;
        }

        public int[][] toArray() {
            return form;
        }
    }

    public static Form FORM1 = new Form(new int[][]{
            {0, 0, 0, 1, 1},
            {0, 0, 2, 1, 1},
            {3, 2, 2, 2, 1},
            {3, 3, 2, 4, 4},
            {3, 3, 4, 4, 4}
    });

    public static Form FORM2 = new Form(new int[][]{
            {0, 0, 0, 0, 1},
            {3, 0, 2, 1, 1},
            {3, 2, 2, 2, 1},
            {3, 3, 2, 4, 1},
            {3, 4, 4, 4, 4}
    });

    public static Form FORM3 = new Form(new int[][]{
            {0, 0, 0, 0, 1},
            {3, 2, 2, 0, 1},
            {3, 3, 2, 1, 1},
            {3, 4, 2, 2, 1},
            {3, 4, 4, 4, 4}
    });

    public static Form[] FORMS = new Form[]{FORM1, FORM2, FORM3};

    public static void registerForm(Form form) {
        List<Form> forms = Arrays.asList(FORMS);
        forms.add(form);
        FORMS = forms.toArray(new Form[0]);
    }


    public Puzzle5(Form form) {
        super(5);
        this.form = form.form;
    }

    @Override
    public Puzzle copy() {
        Puzzle puzzle = new Puzzle5(new Form(this.form));
        for(int row = 0; row < puzzle.size(); row++) {
            for(int col = 0; col < puzzle.size(); col++) {
                puzzle.set(row, col, this.get(row, col));
            }
        }
        return puzzle;
    }
}
