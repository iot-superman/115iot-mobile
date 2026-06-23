package org.example;


class NBAPlayers{
    int age = 28;
    String name;
    //建構方法
    NBAPlayers(String name){
        this.name = name+"先生!!!~~~~~";
    }
    //overload
    NBAPlayers(String name, int age){
//        this.name = name+"先生!!!!!!!!";
        this(name);//必需放在第一行
        this.age = age;
    }
    public void printInfo() {
        System.out.println("name=" + name);
        System.out.println("age=" + age);
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        NBAPlayers obj1 = new NBAPlayers("LeBron James",41);
        obj1.printInfo();
    }
}
