package com.ndn.map;

public class Map6 extends MapType1 {
    public Map6() {
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
    public Map cloneMap() {
        Map map = new Map6();
        for(int row = 0; row < map.size(); row++) {
            for(int col = 0; col < map.size(); col++) {
                map.set(row, col, this.get(row, col));
            }
        }
        return map;
    }
}
