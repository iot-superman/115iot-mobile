#include <stdio.h>
#include <stdlib.h>


int main(void) {

    // ==========================================
    // 1. 定義 struct 結構體 並宣告/初始化變數
    // 定義一個名為 'struct data' 的新資料型別，
    // 它整合了年、月、日這三個相關的整數。
    // 同時宣告兩個變數：
    //   'holiday'：立即初始化為 2026/6/13。
    //   'festival'：未初始化。
    // ==========================================
    struct data {
        int year;
        int month;
        int day;
    } holiday = {2026, 6, 13}, festival;

    // ==========================================
    // 2. 獲取並驗證使用者輸入 (多層 do-while 迴圈)
    // 這裡使用巢狀的 do-while 迴圈來確保使用者
    // 輸入特定日期（2026年6月13日）。
    // 若輸入不符，編譯器會持續提示使用者再次輸入。
    // ==========================================
    do {
        printf("Please enter 2026:");
        // 存取結構體成員使用 點運算子 (.)。
        // scanf 需要成員的記憶體位址，所以前面要加取位址運算子 '&'。
        scanf("%d", &festival.year); 
        fflush(stdin); // 清除輸入緩衝區（雖然在現代C標準中是未定義行為，
                       // 在某些 Windows 編譯器上可以運作，
                       // 這裡通常是為了確保接下來如果有別的讀取不被干擾。）
    } while (festival.year != 2026); // 驗證「年」

    do {
        printf("Please enter 6:");
        scanf("%d", &festival.month);
        fflush(stdin);
    } while (festival.month != 6);    // 驗證「月」

    do {
        printf("Please enter 13:");
        scanf("%d", &festival.day);
        fflush(stdin);
    } while (festival.day != 13);    // 驗證「日」

    // ==========================================
    // 3. 印出結果以驗證
    // 透過點運算子 (.) 來存取結構體變數的內部成員。
    // 格式化字串 %02d 表示：
    //   - d: 整數
    //   - 0: 不足位數補 0
    //   - 2: 寬度為 2。
    //   - 所以這會將 6 印成 06。
    // ==========================================
    printf("\nholiday: %02d/%02d/%d\n", holiday.month, holiday.day, holiday.year);
    printf("festival: %02d/%02d/%d\n", festival.month, festival.day, festival.year);
    
    // sizeof(festival) 會印出整個結構體所佔用的記憶體大小。
    // 因為 struct data 包含三個 int (通常一個 4 位元組)，所以 sizeof 大多數情況下會是 12。
    printf("sizeof(festival)=%d\n", sizeof(festival));

    // 在 Windows 上暫停程式，以便查看輸出結果
    // 註：在 Linux/macOS 上通常不需要這行，或可改用 getchar();
    system("pause");

    return 0;
}
