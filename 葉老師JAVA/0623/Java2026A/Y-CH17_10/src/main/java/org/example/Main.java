package org.example;

//父類別
interface School{
    int num_s = 100;
    default void demo(){
        System.out.println("School...");
    }
    default void test1(){
        System.out.println("test1...");
    }

    void  test2();
    void  test3();
}

//子類別
class Dep implements  School {
    int num_D = 200;

    @Override
    public void test2() {
        System.out.println("test2...");
    }

    @Override
    public void test3() {
        System.out.println("test3...");
    }

    public  void  test4(){
        System.out.println("test4...");
    }

    public  void  demo(){
        System.out.println("Dep...");
    }

}

/*
Ex : DownCasting(向下轉型):
1、本質是一個父類別，但是將它當作子類別來看待，然後將子類別的參考指向
父類別的物件。
2、因為父類別本來就比子類別弱，因此由父轉成子，會有問題。在編譯期間正
常，但在執行期間發生 ClassCastException 的錯誤。
3、**使用時機：當一個子類別物件向上轉型後，基本上這個子類別物件的其它方
法是被遮蔽無法呼叫使用，如果我們想要重新使用這個子類別物件的其它方法，**
 */
//向上轉型

//https://chatgpt.com/s/m_6a323497ebe88191a31934c675cda924
public class Main {
    static void main() {
        System.out.println("upcasting .....");
        //父類別的參考指向子類別的物件
        School obj1 = new Dep();  //upcasting , 向上轉型
        obj1.test1();  //test1~test3 都可以用，非了抽象方法，因為父類別有實作
        obj1.test2();
        obj1.test3();
//        obj1.demo4(); // 編譯錯誤，因為父類別沒有這個方法

        obj1.demo();
        System.out.println("num_s = " + obj1.num_s);
//        System.out.println("num_D = " + obj1.num_D); // 編譯錯誤，因為父類別沒有這個屬性
    System.out.println("");
//自向下轉型
        System.out.println("downcasting .....");
        Dep c = (Dep) obj1; // downcasting , 向下轉型
        c.test1();
        c.test2();
        c.test3();
        c.test4(); // 可以呼叫子類別特有的方法

        c.demo(); // 呼叫子類別的 demo 方法，因為子類別覆寫了父類別的 demo 方法
        System.out.println("num_s = " + c.num_s); // 可以訪問父類別的屬性
        System.out.println("num_D = " + c.num_D); // 可以訪問子類別的屬性
    }
}
