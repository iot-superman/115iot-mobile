
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    static  void main() {
        // ✅ 建立 ArrayList 並初始化
        ArrayList<String> fruits = new ArrayList<>(
                Arrays.asList("Apple", "Banana", "Cherry", "Date")
        );

        // ✅ 取得 Iterator
        Iterator<String> it = fruits.iterator();

        // ✅ 逐一取出元素
        while (it.hasNext()) {
            System.out.println(it.next());   // ⭐ 重點：用 next() 才是元素
        }
    }
}
