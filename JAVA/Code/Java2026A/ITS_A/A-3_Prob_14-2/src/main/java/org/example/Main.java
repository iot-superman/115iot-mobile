package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        double [] db = {1.5, 5.4, 2.3, 9.8};
        System.out.println("Max:" + new Main().getMax(db));    //non-static 要new Main()才能调用
    }

    public  double getMax(double[] array){
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if ( max < array[i]) {
                max = array[i];
            }
        }
        return max;

        //回圈要從0開始掃完整
//        double max = array[2];  //max可以隨便取2，但要確保不會超出陣列範圍，通常會取第一個元素
//        for (int i = 0; i < array.length; i++) {
//            if ( max < array[i]) {
//                max = array[i];
//            }
//        }
//        return max;
    }

}
