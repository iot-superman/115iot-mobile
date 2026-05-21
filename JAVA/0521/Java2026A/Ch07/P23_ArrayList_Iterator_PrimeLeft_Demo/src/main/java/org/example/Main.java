package org.example;

import java.util.ArrayList;
import java.util.Iterator;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        ArrayList<Integer> aList = new ArrayList<>();
        for(int i=2; i<=100;i++) aList.add(i);
        System.out.println(aList);
        Iterator<Integer>it =  aList.iterator();
        while (it.hasNext()){
            int num = it.next();
            for (int j=2;j<num;j++){
                if(num%j==0) {
                    it.remove();
                    break;
                }
            }
        }

        System.out.println(aList);
        System.out.println("num: "+ aList.size());
    }
}



