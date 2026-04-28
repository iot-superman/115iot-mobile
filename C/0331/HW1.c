//
// Created by User on 2026/3/31.
//

/*

 請修改車輛銷售業績程式，使得程式的輸出分別是業務員1加業務員2的上半
年銷售總業績，和業務員1加業務員2的下半年銷售總業績。

*/

#include <stdio.h>
#include <stdlib.h>
int main(void) {
 int i,j,i2,j2;
 int sum =0;
 int sale[2][4];
 for (i=0; i<2; i++) {
  for (j=0; j<4; j++) {
   printf("Please input the sale of salesman %d Q%d result: \n ",i+1,j+1);
   scanf("%d",&sale[i][j]);
   fflush(stdin);
  }
 }
 printf("***********************************\n");
 for (i=0; i<2; i++) {
  for (j=0; j<2; j++) {
   sum=sum+sale[i][j];
  }
 }
 printf("--------------------------------\n");

 printf("\n2025 Q1-Q2 result for total sales volume of %d cars\n",sum);
 sum=0;
 for (i2=0; i2<2; i2++) {
  for (j2=2; j2<4; j2++) {
   sum=sum+sale[i2][j2];
  }
 }
 printf("\n2025 Q3-Q4 result for total sales volume of %d cars\n",sum);

 system("pause");
 return 0;
}
