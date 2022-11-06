package vn.com.orismaster;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Puzzle7 extends Puzzle {
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
            {0, 0, 0, 1, 1, 1, 1},
            {0, 0, 0, 1, 1, 1, 4},
            {2, 2, 0, 3, 3, 4, 4},
            {2, 2, 3, 3, 3, 4, 4},
            {2, 2, 3, 3, 6, 4, 4},
            {2, 5, 5, 5, 6, 6, 6},
            {5, 5, 5, 5, 6, 6, 6}
    });

    public static Form FORM2 = new Form(new int[][]{
            {0, 0, 1, 1, 1, 1, 2},
            {0, 0, 0, 1, 1, 1, 2},
            {3, 0, 0, 4, 4, 2, 2},
            {3, 3, 4, 4, 4, 2, 2},
            {3, 3, 4, 4, 6, 6, 2},
            {3, 5, 5, 5, 6, 6, 6},
            {3, 5, 5, 5, 5, 6, 6}
    });

    public static Form FORM3 = new Form(new int[][]{
            {0, 0, 0, 1, 2, 2, 2},
            {0, 0, 1, 1, 1, 1, 2},
            {0, 0, 4, 1, 4, 1, 2},
            {3, 3, 4, 4, 4, 2, 2},
            {3, 5, 4, 5, 4, 6, 6},
            {3, 5, 5, 5, 5, 6, 6},
            {3, 3, 3, 5, 6, 6, 6}
    });

    public static Form FORM4 = new Form(new int[][]{
            {0, 0, 0, 1, 1, 2, 2},
            {0, 0, 1, 1, 1, 2, 2},
            {0, 0, 3, 1, 1, 2, 2},
            {4, 4, 3, 3, 3, 2, 5},
            {4, 4, 3, 3, 3, 5, 5},
            {4, 4, 6, 6, 6, 5, 5},
            {4, 6, 6, 6, 6, 5, 5}
    });

    public static Form FORM5 = new Form(new int[][]{
            {0, 0, 0, 0, 0, 0, 1},
            {2, 2, 3, 3, 0, 1, 1},
            {2, 2, 2, 3, 1, 1, 4},
            {2, 5, 5, 3, 1, 1, 4},
            {2, 5, 5, 3, 4, 4, 4},
            {5, 5, 6, 3, 3, 4, 4},
            {5, 6, 6, 6, 6, 6, 6}
    });

    public static Form[] FORMS = {FORM1, FORM2, FORM3, FORM4, FORM5};

    public static void registerForm(Form form) {
        List<Form> forms = Arrays.asList(FORMS);
        forms.add(form);
        FORMS = forms.toArray(new Form[0]);
    }

    public Puzzle7(Form form) {
        super(7);
        this.form = form.form;
    }

    @Override
    public Puzzle copy() {
        Puzzle7 puzzle = new Puzzle7(new Form(this.form));
        puzzle.form = this.form;
        for(int row = 0; row < puzzle.size(); row++) {
            for(int col = 0; col < puzzle.size(); col++) {
                puzzle.set(row, col, this.get(row, col));
            }
        }
        return puzzle;
    }
    


}
