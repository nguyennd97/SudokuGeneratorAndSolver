package com.ndn.map;

public class Map12 extends MapType1 {
    public Map12() {
        super(12);
    }

    @Override
    public int getWith(){
        return 3;
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
        return 3;
    }

    @Override
    public Map cloneMap() {
        Map map = new Map12();
        for(int row = 0; row < map.size(); row++) {
            for(int col = 0; col < map.size(); col++) {
                map.set(row, col, this.get(row, col));
            }
        }
        return map;
    }
}
