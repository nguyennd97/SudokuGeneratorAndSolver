package vn.com.orismaster;

import java.util.Arrays;
import java.util.List;

public class Puzzle6 extends Puzzle {
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
            {0, 0, 0, 0, 1, 1},
            {0, 0, 2, 1, 1, 1},
            {2, 2, 2, 1, 3, 3},
            {2, 2, 4, 3, 3, 3},
            {4, 4, 4, 3, 5, 5},
            {4, 4, 5, 5, 5, 5}
    });

    public static Form FORM2 = new Form(new int[][]{
            {0, 0, 0, 0, 0, 1},
            {2, 0, 2, 1, 1, 1},
            {2, 2, 2, 1, 1, 3},
            {2, 4, 4, 3, 3, 3},
            {4, 4, 4, 3, 5, 3},
            {4, 5, 5, 5, 5, 5}
    });

    public static Form[] FORMS = new Form[]{FORM1, FORM2};

    public static void registerForm(Form form) {
        List<Form> forms = Arrays.asList(FORMS);
        forms.add(form);
        FORMS = forms.toArray(new Form[0]);
    }

    public Puzzle6(Form form) {
        super(6);
        this.form = form.form;
    }

    public Puzzle6() {
        super(6);
    }

    @Override
    public int getWith(){
        return 2;
    }

    @Override
    public int getHeight() {
        return 3;
    }

    @Override
    public int getWithOfBox() {
        return 3;
    }

    @Override
    public int getHeightOfBox() {
        return 2;
    }

    @Override
    public Puzzle copy() {
        Puzzle puzzle;
        if (isClassicForm()) {
            puzzle = new Puzzle6();
        } else {
            puzzle = new Puzzle6(new Form(this.form));
        }

        for(int row = 0; row < puzzle.size(); row++) {
            for(int col = 0; col < puzzle.size(); col++) {
                puzzle.set(row, col, this.get(row, col));
            }
        }
        return puzzle;
    }
}
