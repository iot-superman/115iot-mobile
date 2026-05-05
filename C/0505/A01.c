// https://chatgpt.com/s/m_69f9420b32f081918699a6bbfe885020
#include <stdio.h>
#include <stdlib.h>

int main() {
  int x;
  printf("Please enter a decimal number: ");
  scanf("%d", &x);
  printf("Decimal of x is: %d ,Binary of x is: %d\n", x, deicmalToBinary(x));

  system("pause");
  return 0;
}

int deicmalToBinary(int n) {
  int binary = 0;
  int times = 1;
  int rem;
  int i = 1;
  while (n != 0) {
    rem = n % 2;
    printf("loop %d reaindre=%d\n", i++, rem);
    n = n / 2;
    binary += rem * times;
    times = times * 10;
  }
  return binary;
}
