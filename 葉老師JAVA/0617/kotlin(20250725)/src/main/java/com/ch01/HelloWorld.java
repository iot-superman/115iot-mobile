package com.ch01;

import java.util.ArrayList;

public class HelloWorld {
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(90);
        scores.add(75);
        scores.add(88);
        scores.add(60);

        ArrayList<Integer> result = new ArrayList<>();
        for (int s : scores) {
            if (s > 80) {
                result.add(s);
            }
        }

        System.out.println(result);
    }
}

