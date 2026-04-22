#include <stdio.h>
#include <stdlib.h>
/*�إ�C�y�������*/

// float�N�i�H�ΤF
int is_prime(int); // �P�_�O�_�����

int main(void) {
  int i;
  for (i = 2; i <= 100; i++) {
    if (is_prime(i)) {
      printf("%3d", i);
    }
  }

  return 0;
}
int is_prime(int num) {
  int i;
  if (num < 2) return 0;
  for (i = 2; i * i <= num; i++) {
    if (num % i == 0) {
      return 0;
    }
  }
  return 1;
}
