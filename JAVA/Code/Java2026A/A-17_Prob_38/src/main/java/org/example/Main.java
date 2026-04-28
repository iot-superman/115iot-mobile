package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private int id = 1 ;                  //instance attribute
    static void main() {
        Main stduent = new Main();   //create a instance
        stduent.id =  3;
        stduent.displayId();

    }
    protected void displayId() {   //instance method
        System.out.println(id);
    }
}
