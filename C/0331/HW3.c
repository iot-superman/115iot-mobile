	//
// Created by User on 2026/3/31.
//

/*

3.試撰寫一程式，宣告一個具有 8 個元素的整數陣列 eight，然後利用鍵盤輸
入數字，將陣列內的 8 個元素設值，最後於程式裡印出這 8 個元素且計算總
合值。

*/

#include <stdio.h>
#include <stdlib.h>
int main(void) {
 int sum=0, eight[8];
 int i;
 for (i=0; i<8; i++) {
  printf("Please input the number%d is:",i+1 );
  scanf("%d",&eight[i]);
  fflush(stdin);
 }
 printf("The 8 numbers are:\n");
 for (i=0; i<8; i++) {
  printf("%d ",eight[i]);
  sum=sum+eight[i];
 }
 printf("\nThe sum of 8 numbers is:%d\n",sum);
 system("pause");
 return 0;
}
