//https://chatgpt.com/s/m_69f01df1608c8191b739f2aa43bc41fe
#include <stdlib.h>
#include <stdio.h>    
int main(void) {

     int score[4]={77,88,66,57};
    printf("score=%d,*(score+0)=%d\n",score[0],*(score+0));
    printf("score=%d,*(score+1)=%d\n",score[1],*(score+1));
    printf("score=%d,*(score+2)=%d\n",score[2],*(score+2));
    printf("score=%d,*(score+3)=%d\n",score[3],*(score+3));
 
    system("pause");
    return 0;
}
