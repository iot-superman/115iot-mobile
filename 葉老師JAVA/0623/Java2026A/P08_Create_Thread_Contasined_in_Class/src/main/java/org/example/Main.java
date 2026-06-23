//https://chatgpt.com/s/m_6a0ebb44b968819194fae4784aa41980
package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //Create Thread Contasined in Class
        RunTortoise runTortoise = new RunTortoise("runTortoise");
        RunRabbit runRabbit = new RunRabbit("runRabbit");


        runTortoise.t.start();
        runRabbit.t.start();
        System.out.println(Thread.currentThread().getName() + "Thread Ends");

    }
 }


class RunRabbit implements Runnable {
    /**
     * 儲存此 Runnable 對應的 Thread 物件。
     * 目前為 package-private（預設存取權），可供外部直接存取（例如呼叫 {@code t.start()}）。
     * 若需更嚴格的封裝，請將此欄位設為 private 並提供啟動/停止等方法。
     */
    Thread t;

    /**
     * 建構子：建立一個以此 RunRabbit（this）為目標的 Thread，並設定執行緒名稱。
     *
     * @param name 要設定給 Thread 的名稱（可在除錯或日誌中辨識執行緒）
     */
    RunRabbit(String name) {
        t = new Thread(this, name);
    }

    /**
     * Runnable 的執行體（run 方法）。
     *
     * 在此方法內實作執行緒啟動後要執行的邏輯。範例中目前為空實作，使用者應填入實際工作內容，例如：
     * <pre>
     * @Override
     * public void run() {
     *     for (int i = 0; i < 10; i++) {
     *         System.out.println(Thread.currentThread().getName() + \" -> \" + i);
     *     }
     * }
     * </pre>
     *
     * 注意：
     * - 此方法會在 {@code t.start()} 呼叫後由 JVM 的執行緒機制所執行，請避免在此方法中執行阻塞且未處理的長時間操作，或在需要時適當地處理 InterruptedException。
     */
    @Override
    public void run() {
        for (int i = 0; i <= 1000; i += 3) {
            System.out.printf("兔子共跑 %d公里\n", i);
        }
        System.out.println(Thread.currentThread().getName() + "Thread Ends.");
    }
}

class RunTortoise implements Runnable {
    Thread t;

    RunTortoise(String name) {
        t = new Thread(this, name);
    }

    @Override
    public void run() {

        for (int i = 0; i <= 1000; i++) {
            System.out.printf("鳥龜共跑 %d公里\n", i);
        }
        System.out.println(Thread.currentThread().getName() + "Thread Ends.");

    }
}