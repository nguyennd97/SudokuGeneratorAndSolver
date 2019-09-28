package com.ndn.map;

public class Map8 extends MapType1 {

    public Map8() {
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
    public Map cloneMap() {
        Map map = new Map8();
        for(int row = 0; row < map.size(); row++) {
            for(int col = 0; col < map.size(); col++) {
                map.set(row, col, this.get(row, col));
            }
        }
        return map;
    }
}
