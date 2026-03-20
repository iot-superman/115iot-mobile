#include<stdio.h>
#include <stdlib.h>

int main(void)
{
    int score[5],i;
    score[0] = 78;
    score[1] = 71;
    score[2] = 62;
    score[3] = 47;
    score[4] = 80;

    for (i=0; i<5; i++)
    {
        printf("score[%d]=%d\n", i, score[i]);
    }


    system("pause");

    return 0;


}
