package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int[] score = {90,95,80,79,92};
    int max,min;
    max =score[0];
    min =score[0];
    for (int e:score){
        if (e>max){
            max =e;
        }
        if (e<min) {
            min =e;
        }
    }
    System.out.println("Max:" + max);
    System.out.println("Min:"+ min);

    }
}
