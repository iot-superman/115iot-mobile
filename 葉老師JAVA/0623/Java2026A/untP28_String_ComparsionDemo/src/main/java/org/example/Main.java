package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    String s1 ="Welcome to Java";
    String s2 ="Welcome to C++";
    System.out.println(s1.compareTo(s2));  // J - C   =7
    System.out.println(s2.compareTo(s1));  // C- J  =-7

    boolean b1="Welcome to Wnderful Java".startsWith("Welcome");
    boolean b2="Welcome to Wnderful Java".endsWith("Java");
// regionMatches(本字串起始, 目標字串, 目標字串起始, 比對長度)
        // 比對 s1 和 s2 從索引 0 開始的前 12 個字元 (即 "Welcome to ")
    boolean b3 = s1.regionMatches(0,s2,0,12);  //// 修正：長度應為 11 或 12 取決於你想比到哪
        System.out.println(b3+" "+b3);
        System.out.println(b1+" "+b2);
    }
}
