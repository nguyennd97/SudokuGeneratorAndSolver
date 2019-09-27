package com.ndn;

import com.ndn.map.*;

public class Randomer {
    public static Map random(int size) {
        Map map = null;
        switch (size) {
            case 4:
                map = new Map4();
            case 6:
                map = new Map6();
            case 8:
                map = new Map8();
            case 9:
                break;
            case 12:
                map = new Map12();
            case 16:
                map = new Map16();
            case 25:
                map = new Map25();
                break;
            case 5:
            case 7:
                break;
            case 3:
                break;
        }

        return map;
    }
}
