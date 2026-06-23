package org.example;

class Emp{
    public int salary;
    public void showSal(){
        System.out.println("salary:"+salary);
    }
}
class Manager extends Emp{
    public int bonus;//後加
    public void showBonus(){//後加
        System.out.println("bonus:"+bonus);
    }
    //override
    public void showSal(){//後加
        System.out.println("salary+bonus:"+(salary+bonus));
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
//        Manager m = new Manager();
//        m.salary=30000;
//        m.showSal();

        Manager m = new Manager();
        m.salary = 30000;
        m.bonus = 4000;
        m.showSal();
    }
}
