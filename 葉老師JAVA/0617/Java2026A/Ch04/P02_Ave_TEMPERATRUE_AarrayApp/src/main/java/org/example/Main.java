package org.example;

class Person{
    int age;
    String name;
    static String classifcation = "ManKind";   //global attribute;


}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        double[] days = new double[7];

        days[0] = 21.3;
        days[1] = 21.3;
        days[2] = 21.3;
        days[3] = 21.3;
        days[4] = 21.3;
        days[5] = 21.3;
        days[6] = 21.3;
        double avg;
        avg = 0;
        for (double e:days){
            avg +=e;
        }
        avg = avg/days.length;
        System.out.printf("AVG Temperature of days: %.3f", avg);

        double [] day2 = {22.0,21.1,21.2,21.3,21.4,21.5,21.6};
        avg = 0;
        avg =0;
        for (double e: day2){
            avg +=e;
        }
        avg =avg/day2.length;
        System.out.printf("AVG Temperatrue of days2: %.3f\n",avg);
        System.out.printf("5th Temperatrue of days: %.3f\n",days[5]);
        System.out.printf("5th Temperatrue of days: %.3f\n",days[6]);

        Person p1 = new Person();
                p1.age =12;
               p1.name ="Andy";
               System.out.println("p1.name" +p1.name);
               System.out.println("p1.age" +p1.age);
               System.out.println("p1.classifcation:"+Person.classifcation);

        Person p2 = new Person();

        p2.age =36;
        p2.name ="Bill";
        System.out.println("p2.name" +p2.name);
        System.out.println("p2.age" +p2.age);
        System.out.println("p2.classifcation:"+Person.classifcation);

        Person.classifcation = "Mankind2";
        System.out.println("p1.classifcation:"+Person.classifcation);
        System.out.println("p2.classifcation:"+Person.classifcation);


    }
}
