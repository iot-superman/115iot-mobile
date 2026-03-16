// https://youtu.be/bfwWJPOACmA
#include <stdlib.h>
#include <stdio.h>

int main(void)
{
   
   int x=65,y='B';
   char A='A', B='B';
   float fx=65.0f;
   printf("%d==%c=%d\n",x,A,x==A);
   printf("%d>%c=%d\n",x,B,x>B);
   printf("%d>%c=%d\n",B,A,B>A);
   printf("%c>%c=%d\n",y,A,y>A);
   printf("%.1f>%c=%d\n",fx,A,fx>B);
   printf("%.1f==%c=%d\n",fx,A,fx==A);
   
   
   
   
   
   system("pause");
   return 0;
}
