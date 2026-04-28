package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        GTClass<Integer> igt =new GTClass<>();
        GTClass<Integer> igt2 =new GTClass<>(234);
        System.out.println("ig value: " + igt.getValue()+", ig2 value: " + igt2.getValue());

        GTClass<String> sg =new GTClass<>();
        GTClass<String> sg2 =new GTClass<>("Andy");
        System.out.println("sg value: " + sg.getValue()+", sg2 value: " + sg2.getValue());

        sg.setValue("Bill");
        sg2.setValue("Carol");
        System.out.println("sg value: " + sg.getValue()+", sg2 value: " + sg2.getValue());

    }

    static class GTClass<T> {
        private T value;
        GTClass(){}            //default constructor(CTOR) 內定建構子
        GTClass(T value) {     //standard constructor(CTOR) 標準建構子
            this.value = value;
        }
        T getValue() {
            return value;
        }
        void setValue(T value) {
            this.value = value;
        }

    }
}
