package org.example;

/**
 * 泛型鍵值對類別
 *
 * 這是一個支援兩個泛型類型參數的通用類別，用於存儲鍵(Key)和值(Value)的配對。
 * 類似於Java標準庫中的 Map.Entry 介面。
 *
 * @param <K> 鍵的類型（Key Type）
 * @param <V> 值的類型（Value Type）
 *
 * @version 1.0
 * @author Teacher
 */
class Test<K,V> {
    /** 用於存儲鍵的泛型變數 */
    private K key;

    /** 用於存儲值的泛型變數 */
    private V value;

    /**
     * Test 類別的建構子
     *
     * 初始化鍵值對物件，接收鍵和值兩個泛型參數。
     *
     * @param key 鍵的值，類型由泛型參數 K 指定
     * @param value 值的值，類型由泛型參數 V 指定
     */
    public Test(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 取得鍵值
     *
     * @return 回傳存儲的鍵，型別為 K
     */
    public K getKey() {
        return this.key;
    }

    /**
     * 取得值
     *
     * @return 回傳存儲的值，型別為 V
     */
    public V getValue() {
        return this.value;
    }
}

/**
 * 主程式類別
 *
 * 演示如何使用泛型鍵值對類別 Test<K,V> 來存儲和取得不同類型的資料。
 *
 * @author Teacher
 */
public class Main {
    /**
     * 主程式方法
     *
     * 建立一個 Test 泛型物件，指定鍵的類型為 Integer，值的類型為 String。
     * 初始化後輸出鍵和值的內容。
     */
    static void main() {
        // 建立 Test 物件：鍵為 Integer (10)，值為 String ("Bill")
        Test<Integer, String> t = new Test<Integer, String>(10, "Bill");

        // 輸出鍵的內容
        System.out.println("key" + t.getKey());

        // 輸出值的內容
        System.out.println("value" + t.getValue());
    }
}