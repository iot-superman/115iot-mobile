//https://chatgpt.com/s/m_69f00aa6f2848191ad875e41b10c87b3
#include <stdlib.h>   // 提供 system() 等函式
#include <stdio.h>    // 提供 printf() 等輸入輸出函式

// 函式宣告：update
// 參數1：整數陣列（實際上會退化為指標 int*）
// 參數2：陣列長度
void update(int [], int);

int main(void)
{
    int i;  // 迴圈控制變數

    // 宣告並初始化一個整數陣列（共6個元素）
    int arr[6] = {23, 46, 37, 57, 42, 9};

    // 輸出更新前的陣列內容
    printf("Before update:\n");

    // 使用 for 迴圈逐一輸出陣列元素
    for (i = 0; i < 6; i++)
    {
        // %2d：輸出寬度為2，方便對齊
        printf("%2d ", arr[i]);
    }

    printf("\n");  // 換行

    // 呼叫 update 函式，傳入陣列與長度
    // ?? C 語言陣列傳遞是「傳址」（傳指標），不是複製整份資料
    // → 因此函式內修改會直接影響原本 arr
    update(arr, 6);

    // 輸出更新後的陣列內容
    printf("After update:\n");

    for (i = 0; i < 6; i++)
    {
        printf("%2d ", arr[i]);
    }

    printf("\n");

    system("pause");  // Windows 暫停畫面（避免視窗直接關閉）
    return 0;         // 正常結束程式
}


// update 函式：將「所有奇數」加 1 → 變成偶數
void update(int arr[], int n)
{
    int i;

    for (i = 0; i < n; i++)
    {
        // 判斷是否為奇數
        // % 是取餘數運算子
        // 奇數 % 2 == 1
        if (arr[i] % 2 == 1){// 若為奇數 → +1
            arr[i]++;   // 例如：23 → 24
        }
    }
}
 
