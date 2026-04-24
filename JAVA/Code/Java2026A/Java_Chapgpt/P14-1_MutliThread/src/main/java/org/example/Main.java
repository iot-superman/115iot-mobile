//圖文解說
//https://chatgpt.com/s/m_69eb28cc4f9c8191ba4a6f1c87e83c25

package org.example;


import java.util.ArrayList;


public class Main {
    static void main() {
        Thread.currentThread().setName("Main_Thread");
        System.out.println(Thread.currentThread().getName() + "Finshed");
        Thread pThreead  = new Thread(new PrimeTask(100));
        pThreead.setName("PrimeThread");
        pThreead.start();

        Thread fThreead  = new Thread(new FatoriaslTask(3));
        fThreead.setName("FatoriaslThread");
        fThreead.start();

System.out.println(Thread.currentThread().getName() + " Finished.");
    }
}


class FatoriaslTask implements Runnable {
    float n;
    FatoriaslTask(float n){
        this.n = n;
    }


    @Override
    public void run() {
        double res=1;
        for(int i = (int) n; i>=1; i--){
           res*=i;
        }
        System.out.println(Thread.currentThread().getName() + "Finished" + "result=" + res);
    }
}

class PrimeTask implements Runnable {
    int n;

    PrimeTask(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        ArrayList<Integer> aList = new ArrayList<>();
        boolean isPrime = true;
        for (int i = 2; i <= n; i++) {
            isPrime =true;
            for (int j = 2; j <= i - 1; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) aList.add(i);
        }
        System.out.println(Thread.currentThread().getName() + "Finished" + " result=" + aList+"SIZE:"+aList.size());
    }
}