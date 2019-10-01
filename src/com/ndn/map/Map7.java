package com.ndn.map;

import com.ndn.Util;

import java.util.Random;

public class Map7 extends MapType2 {
    private int[][][] boxContainer = {
            {
                    {0, 0, 0, 1, 1, 1, 1},
                    {0, 0, 0, 1, 1, 1, 4},
                    {2, 2, 0, 3, 3, 4, 4},
                    {2, 2, 3, 3, 3, 4, 4},
                    {2, 2, 3, 3, 6, 4, 4},
                    {2, 5, 5, 5, 6, 6, 6},
                    {5, 5, 5, 5, 6, 6, 6}
            },
            {
                    {0, 0, 1, 1, 1, 1, 2},
                    {0, 0, 0, 1, 1, 1, 2},
                    {3, 0, 0, 4, 4, 2, 2},
                    {3, 3, 4, 4, 4, 2, 2},
                    {3, 3, 4, 4, 6, 6, 2},
                    {3, 5, 5, 5, 6, 6, 6},
                    {3, 5, 5, 5, 5, 6, 6}
            },
            {
                    {0, 0, 0, 1, 2, 2, 2},
                    {0, 0, 1, 1, 1, 1, 2},
                    {0, 0, 4, 1, 4, 1, 2},
                    {3, 3, 4, 4, 4, 2, 2},
                    {3, 5, 4, 5, 4, 6, 6},
                    {3, 5, 5, 5, 5, 6, 6},
                    {3, 3, 3, 5, 6, 6, 6}
            },
            {
                    {0, 0, 0, 1, 1, 2, 2},
                    {0, 0, 1, 1, 1, 2, 2},
                    {0, 0, 3, 1, 1, 2, 2},
                    {4, 4, 3, 3, 3, 2, 5},
                    {4, 4, 3, 3, 3, 5, 5},
                    {4, 4, 6, 6, 6, 5, 5},
                    {4, 6, 6, 6, 6, 5, 5}
            },
            {
                    {0, 0, 0, 0, 0, 0, 1},
                    {2, 2, 3, 3, 0, 1, 1},
                    {2, 2, 2, 3, 1, 1, 4},
                    {2, 5, 5, 3, 1, 1, 4},
                    {2, 5, 5, 3, 4, 4, 4},
                    {5, 5, 6, 3, 3, 4, 4},
                    {5, 6, 6, 6, 6, 6, 6}
            }
    };

    public Map7() {
        super(7);
        this.boxes = boxContainer[(new Random().nextInt(boxContainer.length))];
    }


    @Override
    public Map cloneMap() {
        Map7 map = new Map7();
        map.boxes = this.boxes;
        for(int row = 0; row < map.size(); row++) {
            for(int col = 0; col < map.size(); col++) {
                map.set(row, col, this.get(row, col));
            }
        }
        return map;
    }
    


}
