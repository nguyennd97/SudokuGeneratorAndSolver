package com.ndn.map;

public class Map25 extends MapType1 {
    public Map25() {
        super(25);
    }

    @Override
    public int getWith(){
        return 5;
    }

    @Override
    public int getHeight() {
        return 5;
    }

    @Override
    public int getWithOfBox() {
        return 5;
    }

    @Override
    public int getHeightOfBox() {
        return 5;
    }

    @Override
    public Map cloneMap() {
        Map map = new Map25();
        for(int row = 0; row < map.size(); row++) {
            for(int col = 0; col < map.size(); col++) {
                map.set(row, col, this.get(row, col));
            }
        }
        return map;
    }
}
