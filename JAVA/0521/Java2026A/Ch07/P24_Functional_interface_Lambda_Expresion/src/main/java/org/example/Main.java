package org.example;
//https://chatgpt.com/s/m_6a0e6bb24e28819199132053d7d21896

/*
package org.example;

/**
 * 範例主類：示範如何使用「函數式介面」與匿名類（Anonymous Class）實作。
 *
 * 這個檔案展示：
 *  - 定義一個簡單的函數式介面 {@link MyNumber}，其方法回傳 double 值。
 *  - 使用匿名類（Anonymous Class）即時實作該介面，並呼叫其方法。
 *  - 輸出隨機產生的數值（範例中以 Math.random() * 100）。
 *
 * 注意：
 *  - 此範例主要用來教學匿名類與函數式介面的用法；在實務上若僅有一個抽象方法，
 *    也可用 lambda 表達式來取代匿名類以讓程式更簡潔。
 */
public class Main {
    /**
     * 範例用途的主方法（此處為靜態方法示範用途，非傳統 public static void main(String[] args) 簽名）。
     * <p>
     * 此方法執行步驟：
     * 1. 使用匿名類（Anonymous Class）建立一個 {@link MyNumber} 的實例，
     * 立即實作 getValue() 方法（回傳 0~100 之間的亂數）。
     * 2. 印出該實例呼叫 getValue() 的結果。
     * <p>
     * 範例輸出（每次執行會不同）：
     * 例如： 23.452341234
     * <p>
     * 註：若要改用 lambda 表達式（更簡潔），可改為：
     * MyNumber myNumber = () -> Math.random() * 100;
     */
    static void main() {
        // 使用匿名類（Anonymous Class）立即實作 MyNumber 介面
        MyNumber myNumber = new MyNumber() { // Anonymous Class 範例；必須立即實作 getValue()
            @Override  // 覆寫介面方法，否則會編譯錯誤
            public double getValue() {
                // 回傳 0（含）到 100（不含）的亂數
                return Math.random() * 100;
            }
        };
        // 呼叫並輸出匿名類實例的 getValue() 結果
        System.out.println(myNumber.getValue());

        MyNumber myNum2 = () -> Math.random() * 100; //Lambda Expression 範例；更簡潔
        System.out.println(myNum2.getValue());

        NumberTest numberTest = new NumberTest() {
            @Override
            public boolean test(int n) {
                return (n >= 0) ? true : false;
            }
        };
        System.out.println(numberTest.test(-1));
        System.out.println(numberTest.test(1));

        NumberTest numberTest2 = n -> (n >= 0) ? true : false;//Lambda Expression 範例；更簡潔 ()可省略，因為只有一個參數
        System.out.println(numberTest2.test(-11));
        System.out.println(numberTest2.test(11));

        NumberFunc nf = n -> { //Lamdba Expression
            int res = 1;
            while (n > 1) {         //計算 n 的階乘 n*n-1.......2*1
                res *= n;
                n--;
            }
            return res;
        };

        System.out.println(nf.func(6));

        GenericFunc<String> reverse = new GenericFunc<String>() {   //Anonymous Class
            @Override
            public String func(String s) {
                String res = "";
                for (int i = s.length() - 1; i >= 0; i--) {
                    res += s.charAt(i);
                }
                return res;
            }
        };
        System.out.println(reverse.func("ABCDEF"));

        GenericFunc<String> reverse2 = s -> {    //Lambda Expression
            String res = "";
            for (int i = s.length() - 1; i >= 0; i--) {
                res += s.charAt(i);
            }
            return res;
        };

        System.out.println(reverse2.func("Bill Gates"));


        GenericFunc<Integer> factorial = new GenericFunc<Integer>() {
            @Override
            public Integer func(Integer integer) {
                int res = 1;
                while (integer >= 1) {
                    res *= integer;
                    integer--;
                }
                return res;
            }
        };


        GenericFunc<Integer> factorial2 = integer -> {    //Lambda Expression
            int res = 1;
            while (integer >= 1) {
                res *= integer;
                integer--;
            }
            return res;
        };
        System.out.println(factorial2.func(6));



        System.out.println(factorial.func(5));





    }
}


/**
 * MyNumber 介面（函數式介面風格）
 *
 * 這是一個極簡的介面，定義單一方法 {@code getValue()}，回傳一個 double 值。
 * 因為介面只有一個抽象方法，所以此介面可以作為「函數式介面（Functional Interface）」來使用，
 * 適合用匿名類、匿名內部類，或更常見的 lambda 表達式來實作。
 *
 * 使用建議：
 *  - 若使用 Java 8 以上，建議用 lambda 表達式來實作，例如：
 *      MyNumber n = () -> Math.random() * 100;
 *
 *  - 本介面可用於延遲求值、產生隨機值、或任何會回傳 double 的簡單提供者（supplier）。
 */
interface MyNumber{
    /**
     * 取得一個 double 值。
     *
     * 實作者可依需求回傳固定值、計算結果或動態產生的數值（例如亂數）。
     *
     * @return 一個 double 數值
     */
    double getValue();     //SAM single method
}

//
interface  NumberTest{
    boolean test(int n);
}


interface  NumberFunc{
    int func(int n);
}

//Generic is a 類別產生器，能產生不同類型的類別
interface  GenericFunc<T>{
    T func(T t);
}

