/*

葉老師總復習課程 - Java 程式設計入門
第 9 章 - 類別與物件第 6 節 - 建構子與 Overload 建構子

 */

package org.example;

class NBAPlayers {
    int age = 28;
    String name;

    // 建構子
    NBAPlayers(String name) {
        this.name = name + "先生!";
    }

    // Overload 建構子
    NBAPlayers(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void printInfo() {
        System.out.println("name = " + name);
        System.out.println("age = " + age);
    }
}

public class Main {
    public static void main(String[] args) {

        NBAPlayers player1 = new NBAPlayers("Stephen Curry");
        NBAPlayers player2 = new NBAPlayers("LeBron James", 41);

        player1.printInfo();
        player2.printInfo();
    }
}