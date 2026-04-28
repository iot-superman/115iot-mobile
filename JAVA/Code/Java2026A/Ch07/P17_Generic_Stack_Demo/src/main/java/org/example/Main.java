package org.example;
import java.util.ArrayList;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {    //主程式 class
    static void main() {
        GenericStack<String> strStack = new GenericStack<>();  // 型別參數為 大寫String是參考型別,strStack 是參考變數
        strStack.push("Andy");strStack.push("Bill");strStack.push("Carol");
        strStack.showInfo();
        var e = strStack.pop();
        System.out.println("pop: " + e);
        strStack.showInfo();

        GenericStack<Double> dblStack = new GenericStack<>();   // 型別參數為 大寫Double是參考型別
        dblStack.push(1.23);dblStack.push(4.56);dblStack.push(7.89);
        dblStack.showInfo();
        var ee = dblStack.pop();
        System.out.println("pop: " + ee);
        dblStack.showInfo();

    }
}


class  GenericStack<T> {    //Class Generator  物件生器，T 是型別參數，代表任意型別
  private  ArrayList<T> list = new ArrayList<>();
  int getSize() {
      return list.size();
  }
  T peek() {
      return list.getLast();
  }
  void push(T e) {
      list.add(e);
  }
  T pop() {
     T e = peek();
     list.remove(e);
        return e;
    }

  void showInfo() {
      System.out.println(list);
  }
}