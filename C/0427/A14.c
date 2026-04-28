//https://chatgpt.com/s/m_69eeff6f91d48191943e517de1a89ae3
#include <stdio.h>
#include <stdlib.h>
void swap(int* ,int*);
void rect(int, int*);
int main(void)
{
    int n, total;
    printf("Please input an integer: ");
    scanf("%d",&n);
    fflush(stdin);
    rect(n,&total);
    printf("1+2+....+%d=%d\n",n,total);

    system("pause");

    return 0;
}

void rect(int n, int* total) {
  int i;
  *total = 0;
  for(i = 1; i <= n; i++) {
    *total += i;
  }
}
