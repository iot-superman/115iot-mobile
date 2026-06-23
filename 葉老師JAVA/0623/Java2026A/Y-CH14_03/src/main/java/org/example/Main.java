package org.example;

class  Emp{
    public int salary;
    public void showSal(){
        System.out.println(salary);
    }
}

class Manager extends Emp {  //Manger is a Emp
    public int bonus; //後加的

    public void showBonus() {
        System.out.println("Bonus: " + bonus);
    }

    //overrdie  復寫父類別的方法
    public void showSal() {
        // 示範: 先呼叫父類別原本的 showSal()
//        super.showSal();
        // 再印出子類別擴充後的資訊
        System.out.println("Salary+bonus: " + (salary + bonus));
    }

    // 提供一個方法讓外部可以直接呼叫父類別的 showSal()
    public void showParentSal() {
        super.showSal();
    }


}



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
//    Manager m  = new Manager();
//    m.salary = 30000;
//    m.showSal();

        Manager m  = new Manager();
        m.salary = 30000;
        m.bonus =4000;
        m.showSal();
        //call 父的showSal()
//        m.showParentSal();



    }

}
