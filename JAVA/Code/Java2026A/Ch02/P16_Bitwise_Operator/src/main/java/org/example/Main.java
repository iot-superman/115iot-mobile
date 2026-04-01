package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        byte bt  = (byte) 0b1010_0010;
        byte bt2 = (byte) 0b1111_0010;
                          //1010_0010; -128 +2*16 +2 = -94;   // & ,bitwise AND
                          //1111_0010; -128 +7*16 +2 = -14;  // |,bitwise OR
                          //0101_1101;   5*16 +13 = 93  //~ ,bitwise NOT
                          //0101_0000;   5*16 +0 = 80  // ^,bitwise XOR

        int res;   //自動轉型為 int 類型，因為 byte 類型在運算過程中會被提升為 int 類型
        res = (byte) (bt & bt2);
        System.out.println(res);

            //自動轉型為 int 類型，因為 byte 類型在運算過程中會被提升為 int 類型
        res = (byte) (bt | bt2);
        System.out.println(res);

        res = (byte) (bt ^ bt2); // XOR
        System.out.println(res);


    }
}
