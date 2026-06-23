package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        /*
        這些註釋中的 -1 和 -19 是透過將位運算結果解釋為有符號位元組（byte）類型得出的。
- 對於 OR 運算（a | b），結果是 0b1111_1111，即無符號值為 255。在有符號位元組中，最高位為 1 表示負數，計算為 255 - 256 = -1。
- 對於 XOR 運算（a ^ b），結果是 0b1110_1101，即無符號值為 237。在有符號位元組中，237 - 256 = -19。
程式碼中使用 int 類型儲存結果，所以實際輸出是無符號值（255 和 237），但註釋提供了有符號解釋。
         */
         byte a =       0b0101_1011;   // 127 in    //Bitwise operators
                        // 0000_0000   0 ;AND
                        // 0101_1011   91 (OR)
                        // 0101_1011   91 (XOR)
         byte b = (byte)0b1011_0110;   // -128 in decimal
                       // 0001_00110  16+2 ;AND
                       // 1111_1111   255 (-1) ;OR
                       // 1110_1101   237 (XOR) (-19)
        int res;
        int len;
        res = a & b;
        System.out.println(res);

        res = a | b;
        System.out.println(res);

        res = a ^ b; // XOR
        System.out.println(res);


    }
}
