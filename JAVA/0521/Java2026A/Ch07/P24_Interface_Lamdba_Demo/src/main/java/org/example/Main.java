//GPT圖解
//https://chatgpt.com/s/m_69eb06f44aa88191b36d0cc1b675fbeb


package org.example;

public class Main {

    // ⭐ JVM 入口點
    public static void main() {

        // ===============================
        // 1️⃣ 匿名內部類別 (Anonymous Class)
        // ===============================
        MyNumber myNumber = new MyNumber() {

            @Override
            public double getValue() {
                return 345.678;
            }
        };

        System.out.println("myNumber per disconnect: " + myNumber.getValue());

        // ⭐ 保存舊 reference（Heap 物件仍存在）
        MyNumber tmp = myNumber;

        // ===============================
        // 2️⃣ Lambda 重新指派
        // ===============================
        myNumber = () -> Math.sqrt(144.0);

        System.out.println("myNumber reassigned new: " + myNumber.getValue());
        System.out.println("original myNumber post disconnect: " + tmp.getValue());

        // ===============================
        // 3️⃣ Lambda：判斷偶數
        // ===============================
        NumberTest evenTest = n -> n % 2 == 0;

        System.out.println(evenTest.test(202)); // true
        System.out.println(evenTest.test(123)); // false

        // ===============================
        // 4️⃣ Lambda：階乘（Factorial）
        // ===============================
        NumberFunc nf = n -> {
            int res = 1;
            for (int i = n; i >= 1; i--) {
                res *= i;
            }
            return res;
        };

        System.out.println(nf.func(5)); // 120
    }
}


// ===============================
// Functional Interface（函式式介面）
// ===============================

// ⭐ 單一抽象方法 → 才能用 Lambda
interface MyNumber {
    double getValue();
}

class NoName implements MyNumber {
    @Override
    public double getValue() {
        return 123.456;
    }
}

interface NumberTest {
    boolean test(int n);
}

interface NumberFunc {
    int func(int n);
}