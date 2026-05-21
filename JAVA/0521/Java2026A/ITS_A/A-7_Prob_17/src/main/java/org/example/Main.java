package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        boolean IsMember = true; // 是否為會員
        int ages = 33; // 年齡

        boolean joinMember = false; // 是否加入會員
        double price = conuntPrice(IsMember, ages, joinMember);
        System.out.println("The price is: " + price);

    }

    static  double conuntPrice(boolean isMember, int ages, boolean joinMember) {
        double price = 0;
        if (isMember) {
            if(ages >65) { //答案A
                price = 0;
            } else {     //答案B
                price = 30;  // 30% discount for members
            }
        } else { if (joinMember){  //答案C
                price = 430; // 30% discount for joining members(400會員費+30%折扣)
            }else {   //答案D
                price =130; // Regular price for non-members

            }
        }
        return price;
    }
}
