package vn.com.dangnguyendota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Puzzle4 extends Puzzle {
    public static class Form {
        private int[][] form;
        public Form(String form) {
            this.form = new int[4][4];
            for(int i = 0; i < 16; i++) {
                int row = i / 4;
                int col = i % 4;
                this.form[row][col] = Integer.parseInt(String.valueOf(form.charAt(i))) - 1;
            }
        }

        public Form(int[][] form) {
            this.form = form;
        }
    }

    public static Form FORM1 = new Form(new int[][]{
            {0, 0, 0, 1},
            {0, 2, 1, 1},
            {2, 2, 1, 3},
            {2, 3, 3, 3}
    });

    public static Form FORM2 = new Form(new int[][]{
            {0, 0, 0, 1},
            {0, 1, 1, 1},
            {2, 2, 2, 3},
            {2, 3, 3, 3}
    });

    public static Form[] FORMS = new Form[]{FORM1, FORM2};

    public static void registerForm(Form form) {
        List<Form> forms = Arrays.asList(FORMS);
        forms.add(form);
        FORMS = forms.toArray(new Form[0]);
    }

    public Puzzle4(Form form) {
        super(4);
        this.form = form.form;
    }

    public Puzzle4() {
        super(4);
    }

    @Override
    public int getWith(){
        return 2;
    }

    @Override
    public int getHeight() {
        return 2;
    }

    @Override
    public int getWithOfBox() {
        return 2;
    }

    @Override
    public int getHeightOfBox() {
        return 2;
    }

    @Override
    public Puzzle copy() {
        Puzzle puzzle;
        if (isClassicForm()) {
            puzzle = new Puzzle4();
        } else {
            puzzle = new Puzzle4(new Form(this.form));
        }
        for(int row = 0; row < puzzle.size(); row++) {
            for(int col = 0; col < puzzle.size(); col++) {
                puzzle.set(row, col, this.get(row, col));
            }
        }
        return puzzle;
    }
}
