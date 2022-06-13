package vn.com.dangnguyendota;

import java.util.Arrays;
import java.util.List;

public class Puzzle8 extends Puzzle {
    
    public static class Form {
        private int[][] form;
        public Form(int[][] form) {
            this.form = form;
        }

        public int[][] toArray() {
            return form;
        }
    }
    
    public static Form[] FORMS = new Form[]{};

    public static void registerForm(Form form) {
        List<Form> forms = Arrays.asList(FORMS);
        forms.add(form);
        FORMS = forms.toArray(new Form[0]);
    }
    
    public Puzzle8(Form form) {
        super(8);
        this.form = form.form;
    }

    public Puzzle8() {
        super(8);
    }

    @Override
    public int getWith(){
        return 2;
    }

    @Override
    public int getHeight() {
        return 4;
    }

    @Override
    public int getWithOfBox() {
        return 4;
    }

    @Override
    public int getHeightOfBox() {
        return 2;
    }

    @Override
    public Puzzle copy() {
        Puzzle puzzle;
        if(isClassicForm()) {
            puzzle = new Puzzle8();
        } else {
            puzzle = new Puzzle8(new Form(this.form));
        }
        for(int row = 0; row < puzzle.size(); row++) {
            for(int col = 0; col < puzzle.size(); col++) {
                puzzle.set(row, col, this.get(row, col));
            }
        }
        return puzzle;
    }
}
