package com.ndn.map;

public class MapFactory {

    public static Map random(int size) {
        Map map = null;
        switch (size) {
            case 4:
                return new Map4().random();
            case 6:
                return new Map6().random();
            case 8:
                return new Map8().random();
            case 9:
                return new Map9().random();
            case 12:
                return new Map12().random();
            case 16:
                return new Map16().random();
            case 25:
                return new Map25().random();
            case 5:
                return new Map5().random();
            case 7:
                break;
            case 3:
                break;
        }

        return map;
    }

    public static Map get(int size) {
        Map map = null;
        switch (size) {
            case 4:
                return new Map4();
            case 6:
                return new Map6();
            case 8:
                return new Map8();
            case 9:
                return new Map9();
            case 12:
                return new Map12();
            case 16:
                return new Map16();
            case 25:
                return new Map25();
            case 5:
                return new Map5();
            case 7:
                break;
            case 3:
                break;
        }

        return map;
    }
}
