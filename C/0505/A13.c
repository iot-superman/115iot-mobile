// https://chatgpt.com/s/m_69f987e8f1e08191a2b9b2e68c39142f
// https://gemini.google.com/share/7c2dcd1032d9

/*
if (elapse.minute < 0) {
        elapse.minute += 60; // 分鐘加 60
        end.hour--;          // 小時減 1
    }
    ```
    如果計算出的 `elapse.minute` 是負數（例如本例中 `13 - 32 =
-19`），則意味著需要從「小時」借 1。我們將 `elapse.minute` 加上 60（將 1
小時轉換為 60 分鐘），使其變為正數（`-19 + 60 = 41`）。同時，我們將 `end.hour`
減去 1，以反映借出的那一小時。

3.  **計算小時差：**

```c
    elapse.hour = end.hour - start.hour;
    ```
    最後，我們計算小時差。請注意，此處的 `end.hour`
可能是借位後的值。在本例中，原本是 15，借位後變為 14。所以小時差為 `14 - 12 =
2`。

### 程式碼與圖解說明

我已將程式碼整理成易於閱讀的格式，並詳細註解了各個部分，特別是關鍵的借位邏輯：

```c
*/
#include <stdio.h>
#include <stdlib.h>

int main(void) {
  // ==========================================
  // 1. 定義 struct 結構體 並宣告/初始化變數
  // 定義一個名為 'struct time' 的新資料型別。
  // 它整合了小時和分鐘這兩個相關的整數。
  // 同時宣告並初始化三個變數：
  //   'start'：開始時間 12:32。
  //   'end'：  結束時間 15:13。
  //   'elapse'：經過時間，初始化為 00:00。
  // ==========================================
  struct time {
    int hour;
    int minute;
  } start = {12, 32}, end = {15, 13}, elapse = {0, 0};

  // ==========================================
  // 2. 印出初始時間
  // 使用點運算子 (.) 來存取結構體成員。 '%2d' 表示整數佔 2 格，不足補空白。
  // ==========================================
  printf("start= %2d:%2d\n", start.hour, start.minute);
  printf("end  = %2d:%2d\n", end.hour, end.minute);

  // ==========================================
  // ==========================================
  // ==========================================

  elapse.minute = end.minute - start.minute;

  if (elapse.minute < 0) {
    // 不夠，減去的值比較大，所以要借位
    elapse.minute += 60; // 分鐘加 60
    end.hour--;          // 小時減 1
  }

  elapse.hour = end.hour - start.hour;

  // ==========================================
  // 4. 印出計算結果
  // ==========================================
  printf("elapse= %2d:%2d\n", elapse.hour, elapse.minute);

  system("pause");

  return 0;
}
