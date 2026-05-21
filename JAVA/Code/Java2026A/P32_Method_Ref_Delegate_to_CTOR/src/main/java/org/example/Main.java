package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
//        DelegateCTOR dctor=People::new;
//        People people = dctor.invoke("John");
//        System.out.println(people.name);
 /

        DelegateCTOR doctor = People::new;
        People people = doctor.invoke("Andy");
        System.out.println(people.name);


    }
}



class People{
    String name;
    public People(String str){
        this.name = str;
    }
}

interface DelegateCTOR{
    People invoke(String str);
}
