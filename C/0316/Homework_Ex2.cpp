/* 
  2.請由鍵盤輸入圓的半徑 6，輸出圓的周長及圓的面積。
*/

#include <stdlib.h>
#include <stdio.h>

int main(void)
{
   float r, clong,cpow;
   printf("輸入攝氏圓的半徑: ");
   scanf("%f", &r);
   clong=r*2*3.14;
   cpow=r*r*3.14;
   printf("周長:%f\n",clong);
   printf("面積:%f\n",cpow);   
   system("pause");
   return 0;
}
