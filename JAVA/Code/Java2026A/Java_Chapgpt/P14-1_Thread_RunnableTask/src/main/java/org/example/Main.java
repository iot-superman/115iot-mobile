// 圖文解說：
//https://chatgpt.com/s/m_69eb1ba67098819184aff5033e4b9013
package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Thread.currentThread().setName("Main Thread");
        System.out.println(Thread.currentThread().getName()+"is Now running!!!");
        Thread workThread = new Thread(new RunnableTask());//創建工作線程 //New RunnableTask;
        workThread.setName("WorkThread");   //設定工作線程名稱
        workThread.start();  //hand over to OS; //開始工作線程
      workThread.setPriority(Thread.MAX_PRIORITY);
        System.out.println(workThread.getName()+"is Ending!!!");

        Thread thd_via_Lamda_Task  = new Thread( ()->{      //lamdas expressison 建立一個物件(runnnable)
            for (int i = 1; i <= 120; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            System.out.println(Thread.currentThread().getName()+"is Ending!!!");
        });

        thd_via_Lamda_Task.setName("LamdasThread");
        thd_via_Lamda_Task.setPriority(Thread.MIN_PRIORITY);
        thd_via_Lamda_Task.start();



    }
}

class  RunnableTask implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        System.out.println(Thread.currentThread().getName()+"is Ending!!!");
    }

}
