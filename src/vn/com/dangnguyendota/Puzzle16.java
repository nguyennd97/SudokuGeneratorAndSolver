package vn.com.dangnguyendota;

public class Puzzle16 extends Puzzle {

    public Puzzle16() {
        super(16);
    }

    @Override
    public int getWith(){
        return 4;
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
        return 4;
    }

    @Override
    public Puzzle copy() {
        Puzzle puzzle = new Puzzle16();
        for(int row = 0; row < puzzle.size(); row++) {
            for(int col = 0; col < puzzle.size(); col++) {
                puzzle.set(row, col, this.get(row, col));
            }
        }
        return puzzle;
    }
}
