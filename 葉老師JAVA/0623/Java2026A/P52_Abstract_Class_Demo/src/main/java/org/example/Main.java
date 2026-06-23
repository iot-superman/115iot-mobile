package org.example;

//BOOK: P8-18 
public class Main {
    static void main() {
        
        Rect r = new Rect(10, 20, 30, 40);
        System.out.println("x =" + r.x + "y =" + r.y + " width= "+ r.width + "  height= "+ r.height + "  area= "+ r.computeArea());

        Geo r2 = new Rect(10, 20, 30, 40);
        System.out.println("x =" + r2.x + "y =" + r2.y + " width= "+ ((Rect)r2).width + "  height= "+ ((Rect)r2).height + "  area= "+ r2.computeArea());//強制轉型為Rect類別才可以有width, heiight
    }
}



abstract class Geo{                   //抽象方法  專來給別人(子類別) 來繼承的   , 只可以public /proteected (default(沒有寫）) ,就是不可以ｐｒｉｖａｔｅ， 因為需可給別人看得到 　
    int x,y;
    Geo(int x, int y){
        this.x = x;
        this.y = y;
    }
    abstract int computeArea();  //抽象方法，沒有實作，子類別必須實作
}



class Rect extends Geo{
    int width, height;
    Rect(int x, int y, int width, int height){
        super(x, y);
        this.width = width;
        this.height = height;
    }
     int computeArea() {     //override the abstract method in the abstract class  這邊實作抽象方法，才可以給人建立物件 
        return width * height;
     }
}
