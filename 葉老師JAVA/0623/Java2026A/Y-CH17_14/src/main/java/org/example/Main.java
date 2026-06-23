package org.example;

/*
 PDF :CH17 -P9
17-2  匿名類別  Anonymous (補充  android 常用寫法)
在產生一個物件時，通常會指定類別型態，變數名稱，如  Pokemon p1=new
Pokemon()但有時產生物件時，也懶的給變數名稱，就會形成了匿名物件。


這種寫法在 Java 是沒問題的，因為一直產生出來的 Pikachu()，會被垃圾回收機
制給刪除，所以不會造成記憶体不足。但如果是寫在  C/C++裏，大約二秒後，
記憶体就不足了，連滑鼠也動不了。
 */
interface Animal{
    void running(); //抽象方法(目前只有一個抽象方法，才可以使用 Lambda 運算式)
}

class Dog implements Animal{ //(implements)實作一個介面
    @Override
    public void running() {
        System.out.println("狗在跑...");
    }
}



public class Main {
    static void main() {

//        透過 UpCasting 使用介面功能
//        Ex: UpCasting(向上轉型)：
//        1、將子類別當作父類別看，因此父類別的所有方法、屬性均可使用。
//        2、子類別新增另外的方法與屬性，均不可使用。
//        3、子類別重新定義(Override)方法，以子類別內容為主。
//        4、因為子類別本來就比父類別強大，因此由子轉成父是沒有問題的。
//        5、常使用的時機，若父為 A 介面，子為 B 類別。若想只使用父 A 介面的功能，
//        即可先 new B 物件，然後使用 UpCasting 即可(介面不可以物件化)。

        Animal h1 = new Dog(); //向上轉型，UpCasting
        h1.running();

        // 匿名類別
        // https://chatgpt.com/s/m_6a323d7170cc81919baee9f5b44fef3b
        System.out.println("......2.........");
      // 使用匿名類別實作 Animal 介面
    // 這個匿名物件直接在宣告時定義 running() 方法的實現
    Animal h2 = new Animal() {
        @Override
        public void running() {
            System.out.println("h2.....");
        }
    };
        h2.running();
        System.out.println("......3.........");
        Animal h3 = new Animal() {      //使用匿名類別，來實作介面
            @Override
            public void running() {
                System.out.println("h3.....");
            }
        };
        h3.running();
        System.out.println("......4.........");
        /*
        Lambda
        Lambda 運算式可當成一個方法，可依不同輸入值經處理後傳回輸出值。但
        Lambda 與一般方法不同是，Lambda 不需要替方法命名，Lambda 常用於匿名
        (Anonymous)類別並實作方法的場合上，以便讓 Java 語法更簡潔。

        **Lambda 語法通常用於只有一個 public 方法的介面**

        語法如下：
        (argument-list)->{body}
        (參數列表)->{Lambda 表達式主體}
        參數列表：可以是有參數，也可以沒有參數
        箭頭符號：用於鏈結參數列表和 Lambda 表達式

        參考文獻:
        https://magiclen.org/java-8-lambda/
        https://www.gss.com.tw/blog/java8
         */
        Animal h4 = () -> { //使用 Lambda 運算式，來實作介面 //直接會override
            System.out.println("h4.....");
        };
        h4.running();

    }
}
