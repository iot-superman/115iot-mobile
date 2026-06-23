//https://chatgpt.com/s/m_6a0eb021cce8819189bc4e5a490e2e8a
package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Thread.currentThread().setName("Main Thread");
        Thread tThread =  new Thread(new Runnable() {   //Anonymous Class
            @Override
            public void run() {
                for(int i=0; i<=1000; i++){
                    System.out.printf("鳥龜共跑 %d公里\n",i);
                }
//                System.out.println("Toroise Thread Ends.鳥龜跑完了，開始睡覺...");
                System.out.println(Thread.currentThread().getName() +"Thread Ends.");
            }
        });
        tThread.setName("tThread");
        tThread.start();

        Thread rThread = new Thread(new Runnable() {   //Anonymous Class
            @Override
            public void run() {
                for(int i=0;i<=1000;i+=3){
                    System.out.printf("兔子共跑 %d公里\n",i);
                }
//                System.out.println("Rabbitt Thread Ends.兔子跑完了，開始睡覺...");
                System.out.println(Thread.currentThread().getName() +"Thread Ends.");
            }
        });
        rThread.setName("rThread");
        rThread.start();


//        System.out.println("Main Thread Ends.");
        System.out.println(Thread.currentThread().getName() +"Thread Ends.");

    }
}
