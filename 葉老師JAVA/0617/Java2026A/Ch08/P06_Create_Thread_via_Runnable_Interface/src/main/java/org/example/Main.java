//GPT圖-
//https://chatgpt.com/s/m_69eb1092792c819184fd8b2ac53be10b

package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    //Runnable = 任務（Task）
    //Thread = 執行者（Worker）
    static void main() {       //Thread       //Task
        Thread totoiseThread  = new Thread(new ToroiseRunnale());
        totoiseThread.start();
        Thread rabbitRunnable = new Thread(new RabbitRunnable());
        rabbitRunnable.start();
        System.out.println("Main Thread Ends. 主線程繼續執行其他任務...");

      
    }
}



class  ToroiseRunnale implements Runnable{

    @Override
    public void run() {
        for(int i=0; i<=1000; i++){
            System.out.printf("鳥龜共跑 %d公里\n",i);
        }
        System.out.println("Toroise Thread Ends.鳥龜跑完了，開始睡覺...");
    }
}

class  RabbitRunnable implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<=1000;i++){
            System.out.printf("兔子共跑 %d公里\n",i);
        }
        System.out.println("Rabbitt Thread Ends.兔子跑完了，開始睡覺...");
    }
}
