package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Customer {
    private int id = 3;
    static void main() {
        Customer customer = Customer();

        customer.id = 5 ;
        customer.showId();

    }

    protected  void showId(){
        System.out.println(id);
    }
}
