package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {


            // 第一種：字串池方式
            String msg = "Welcome to Java Programming";

            // 第二種：建構子方式
            String msg2 = new String("This is another Constructing String Style");

            // 修正拼字 length
            System.out.println(msg + ": length " + msg.length());
            System.out.println(msg2 + ": length " + msg2.length());

            // 2. 修正字元陣列中的點號 '.' 為逗號 ','
            char[] charArray = {'w', 'e', 'l', 'c', 'o', 'm', 'e'};

            // 3. 變數名稱不可重複宣告，改用 msg3
            String msg3 = new String(charArray);

            // 4. 變數名稱要對應正確 (msg3)
            System.out.println(msg3 + ": length " + msg3.length());

}}

