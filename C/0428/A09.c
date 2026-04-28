// https://gemini.google.com/share/94112847c765

#include <stdlib.h>
#include <stdio.h>    
int main(void) {

    char *aa="Hello";
    char *bb="World";
    char *tmp;
    printf("Before swap: aa= %s ,bb=%s\n",aa,bb);
    tmp=aa;
    aa=bb;
    bb=tmp;
    printf("After swap:  aa= %s ,bb=%s\n" ,aa,bb);

    // 暫停程式以便查看執行結果
    system("pause");
    return 0;
}


