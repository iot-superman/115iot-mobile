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
     // ?出??后的字符串（大?字母已??小?）
    // 原理：ASCII?中，大?字母A-Z??65-90，小?字母a-z??97-122，?者相差32
    // 因此?大?字母的ASCII值加32即可得到??的小?字母
    printf("Convert uppercase to lowercase: %s\n",str);

    system("pause");
    return 0;
}
