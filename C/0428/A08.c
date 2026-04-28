// 程式：示範字元陣列與字串指標的使用

#include <stdlib.h>
#include <stdio.h>    
int main(void) {

    char name[20]; // 宣告一個可儲存20個字元的字元陣列用於存放使用者姓名

    char *ptr = "How are you?"; // 宣告字串指標，指向字字串常數
    printf("What's your name?\n");
    gets(name);     // 讀取使用者輸入的姓名並儲存到name陣列中
    printf("Hi, %s. ",name);
    // 使用puts函數列印ptr指標所指向的字串
    puts(ptr);

    // 暫停程式以便查看執行結果
    system("pause");
    return 0;
}


