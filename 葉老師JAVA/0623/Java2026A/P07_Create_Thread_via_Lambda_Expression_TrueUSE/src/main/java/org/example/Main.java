//Java 多執行緒範例：鳥龜與兔子賽跑，使用 Lambda 表示法建立 Runnable 物件。
//https://chatgpt.com/s/m_6a0eb3dfea988191837d97937a67be28
package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Thread.currentThread().setName("Main Thread");
        // 使用 Lambda 表示法建立 Runnable（正確語法：不要在 lambda 內使用方法宣告/Override）
        Thread tThread = new Thread(() -> {
            for (int i = 0; i <= 1000; i++) {
                System.out.printf("鳥龜共跑 %d公里\n", i);
            }
             System.out.println(Thread.currentThread().getName() + "Thread Ends.");
        });
        tThread.setName("Lambda_tThread");
        tThread.start();

        // 同樣使用 Lambda 表示法取代匿名內部類別
        Thread rThread = new Thread(() -> {
            for (int i = 0; i <= 1000; i += 3) {
                System.out.printf("兔子共跑 %d公里\n", i);
            }
             System.out.println(Thread.currentThread().getName() + "Thread Ends.");
        });
        rThread.setName("Lambda_rThread");
        rThread.start();



        System.out.println(Thread.currentThread().getName() +"Thread Ends.");

    }
}
