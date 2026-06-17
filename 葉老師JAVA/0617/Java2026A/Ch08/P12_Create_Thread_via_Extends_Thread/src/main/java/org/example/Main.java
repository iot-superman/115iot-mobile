//P12_Create_Thread_via_Extends_Thread
//https://chatgpt.com/s/m_6a0ebf63ede88191a6df486803a55b24
package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    Thread.currentThread().setName("Main Thread");
    RabbitThread rabbitThread = new RabbitThread("Rabbit Thread");
    TortoiseThread tortoiseThread = new TortoiseThread("Tortoise Thread");
    rabbitThread.start();
    tortoiseThread.start();
    System.out.println(Thread.currentThread().getName() + "Thread Ends.");
    }
}


class RabbitThread extends Thread {
    RabbitThread(String name) {
        super(name); // 呼叫 Thread 的建構子，設定執行緒名稱
    }
    @Override
    public void run() {
        for (int i = 0; i <= 1000; i += 3) {
            System.out.printf("兔子共跑 %d公里\n", i);
        }
        System.out.println(Thread.currentThread().getName() + "Thread Ends.");
    }
}



class TortoiseThread extends Thread {
    TortoiseThread(String name) {
        super(name); // 呼叫 Thread 的建構子，設定執行緒名稱
    }
    @Override
    public void run() {
        for (int i = 0; i <= 1000; i++) {
            System.out.printf("鳥龜共跑 %d公里\n", i);
        }
        System.out.println(Thread.currentThread().getName() + "Thread Ends.");
    }
}