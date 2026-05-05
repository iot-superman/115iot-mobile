#include <stdio.h>
#include <stdlib.h>

int main() {
    // 1. 定義結構體 (struct)
    // 結構體是一種自定義的資料型別，允許你將不同型別的資料整合在一起。
    struct data {
        char name[20]; // 姓名成員：一個包含 20 個字元的字元陣列（字串）
        int math;      // 數學分數成員：一個整數
    };

    // 2. 宣告結構體變數
    // 這裡我們宣告了一個名為 'student' 的變數，它的型別是剛定義的 'struct data'。
    struct data student;

    // 3. 獲取使用者輸入
    printf("enter name:");
    // scanf 讀取字串時，'%s' 會在遇到空格或換行符號時停止。
    // 因為 'student.name' 本身就是一個陣列名稱（代表位址），所以不需要在前面加 '&'。
    scanf("%s", student.name);

    printf("enter math:");
    // 讀取整數時，'%d' 需要一個記憶體位址來儲存值，
    // 因此我們使用 '&student.math' 來取得 'math' 成員的位址。
    scanf("%d", &student.math);

    // 4. 清除輸入緩衝區 (可選但建議)
    // 當使用者輸入數字並按下 Enter 後，換行符號 (\n) 會留在緩衝區中。
    // fflush(stdin) 在標準 C 語言中是未定義行為，不建議使用。
    // 在這裡它的目的通常是為了確保接下來如果有其他 scanf 讀取字元或字串時，
    // 不會被留在緩衝區中的換行符號干擾。
    // (在某些 Windows 編譯器上可以運作，但不可移植)
    fflush(stdin);

    // 5. 印出結構體成員的值
    printf("name=%s, math Score=%d", student.name, student.math);

    // 程式結束前暫停 (Windows 適用)
    // // |b Windows ?W?E°±μ{|!!A￥H?K?d?Y?e￥Xμ2aG
    // // μu!G|b Linux/macOS ?W3q±`?￡?Y-n3o|a!AcI￥i§i￥I getchar();
    system("pause");

    return 0;
}
