package org.example;

import java.util.ArrayList;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        ArrayList<String> hobbies = new ArrayList<>(Arrays.asList("Reading"));
        Person p1 = new Person("Adny", hobbies);
        System.out.println("p1.hobbies: " + p1.hobbies);
        Person p2 = p1.clone();
        p2.name ="Bill";
        System.out.println("p2.hobbies: " + p2.hobbies);


        p1.hobbies.add("Writing");
        p1.hobbies.add("Swimming");

        System.out.println("p1.hobbies: " + p1.name);
        System.out.println("p2.hobbies: " + p2.name);
        System.out.println("p1.hobbies: " + p1.hobbies);
        System.out.println("p2.hobbies: " + p2.hobbies);



    }
}
class Person implements Cloneable {
    String name;
    ArrayList<String> hobbies;

    Person(String name, ArrayList<String> hobbies) {  //Standard constructor (CTOR);
        this.name = name;
        this.hobbies = hobbies;
    }

    @Override
    public Person clone() {
        try {
            //深copy
            Person p = (Person) super.clone();
            p.name = new String("");       //要新的
            p.hobbies = new ArrayList<>(this.hobbies); //要新的
            return  p;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}