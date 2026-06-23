package org.example;

class   MyData<T> {  //泛型類別
    private T obj;	// 泛型資料
    void setobj(T obj) {
        this.obj = obj;					// 設定資料
    }
    T getobj() {
        return this.obj;				// 回傳資料
    }
}


public class Main {
    static void main() {
        MyData<Integer> i = new MyData<Integer>();
        i.setobj(10);
        System.out.println("value="+i.getobj());

        MyData<Double> d = new MyData<Double>();
        d.setobj(12.5555);
        System.out.println("value="+d.getobj());

        MyData<String> st = new MyData<String>();
        st.setobj("Hello");
        System.out.println("value="+st.getobj());
    }
}
