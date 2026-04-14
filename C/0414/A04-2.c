#include <stdio.h>
#include <stdlib.h>

int main(void){
    int i=0;
    char str[50];
    printf("Please input a string:\n");
    gets(str);

    while(str[i]!='\0'){
        if (str[i]>=65 && str[i]<=90){
            str[i]=str[i]+32;
        }
        i++;
    }
    // 輸出轉換後的字串（大寫字母已轉為小寫）
    // 原理：ASCII碼中，大寫字母A-Z對應65-90，小寫字母a-z對應97-122，兩者相差32
    // 因此將大寫字母的ASCII值加32即可得到對應的小寫字母
    printf("Convert uppercase to lowercase: %s\n",str);

    system("pause");
    return 0;
}
