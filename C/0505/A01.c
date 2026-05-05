//https://chatgpt.com/s/m_69f940d14e8c8191a1431f2f99a54670
#include <stdio.h>
#include <stdlib.h>

int main() {
  int x;
  printf("Please enter a decimal number: ");
  scanf("%d", &x);
  printf("Decimal of x is: %d ,Binary of x is: %d\n", x, deicmalToBinary(x));

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
