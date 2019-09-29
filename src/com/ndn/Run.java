package com.ndn;

import com.ndn.map.*;

public class Run {
    public static void main(String... args) {
        Map map = Generator.generateRandomMap(25);
        System.out.println(map);
        System.out.println(map.getScore());
    }
}
