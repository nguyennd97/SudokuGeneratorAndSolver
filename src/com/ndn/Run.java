package com.ndn;

import com.ndn.map.Map4;
import com.ndn.map.Map6;
import com.ndn.map.Map9;

public class Run {
    public static void main(String... args) {
        System.out.println(new Map4().random());
        System.out.println(new Map6().random());
        System.out.println(new Map9().random());
    }
}
