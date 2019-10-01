package com.ndn.map;

import com.ndn.Util;

public class Map5 extends MapType2{

    public Map5() {
        super(5);
        boxes = new int[][]{
                {0, 0, 0, 1, 1},
                {0, 0, 2, 1, 1},
                {3, 2, 2, 2, 1},
                {3, 3, 2, 4, 4},
                {3, 3, 4, 4, 4}
        };
    }


    @Override
    public Map cloneMap() {
        Map map = new Map5();
        for(int row = 0; row < map.size(); row++) {
            for(int col = 0; col < map.size(); col++) {
                map.set(row, col, this.get(row, col));
            }
        }
        return map;
    }
}
