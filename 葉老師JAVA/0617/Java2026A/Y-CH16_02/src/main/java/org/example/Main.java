//https://chatgpt.com/s/m_6a31f84c8f7481919d7d6d3d24499863

package org.example;

abstract  class  Cal{
    public int x;
    public int y;
    Cal(int x ,int y){
        this.x =x;
        this.y =y;
        System.out.println("初始化數值");
    }

    double mul(){
        return  x*y;
    }
    abstract double answer();  //抽象方法

}

class  CalPlus extends Cal{

    CalPlus(int x, int y) {   //因父類別有建購方法 這一並也要寫 也要有建構方法
        super(x, y);
    }

    @Override
    double answer() {
        return x+y;
    }
}
class CalMinus extends  Cal{


    CalMinus(int x, int y) {
        super(x, y);
    }

    @Override
    double answer() {
        return x-y;
    }
}



public class Main {
    static void main() {




    }
}
