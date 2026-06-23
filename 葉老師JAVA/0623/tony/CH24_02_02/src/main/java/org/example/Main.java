package org.example;

class Test<K,V>{
    private K key;
    private V value;
    public Test(K key,V value){
        this.key = key;
        this.value = value;
    }
    public K getKey() {
        return this.key;
    }
    public V getValue() {
        return this.value;
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //Test<Integer, String> t = new Test<>(10,"Bill");
        Test<Integer, String> t = new Test<Integer, String>(10,"Bill");
        System.out.println("key"+t.getKey());
        System.out.println("value"+t.getValue());
    }
}
