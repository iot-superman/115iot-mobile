package org.example;
//泛型類別
class MyData<T>{
    private T obj;
    public void setobj(T obj){
        this.obj = obj;
    }
    T getObj(){
        return this.obj;
    }
}

public class Main {
    static void main() {
        MyData<Integer> i = new MyData<Integer>();
        i.setobj(10);
        System.out.println("value="+i.getObj());

        MyData<Double> d = new MyData<Double>();
        d.setobj(12.5555);
        System.out.println("value="+d.getObj());

        MyData<String> st = new MyData<String>();
        st.setobj("Hello");
        System.out.println("value="+st.getObj());
    }
}
