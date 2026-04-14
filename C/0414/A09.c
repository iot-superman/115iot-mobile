#include <stdio.h>
#include <stdlib.h>
/*建立C語言的函數*/
void display(char ,int);

int main(void)
{
  int sum;
  char ch;
  printf("請輸入一個字元：");
  scanf("%c",&ch);
  fflush(stdin);
  printf("How many characters do you want to print: ");
  scanf("%d",&sum);
  fflush(stdin);
  display(ch, sum);
  system("pause");
  return 0;
}

void display(char ch,int sum){
    int i;
    for(i=1;i<sum;i++){
        printf("%c",ch);
    }
    printf("\n");
}
