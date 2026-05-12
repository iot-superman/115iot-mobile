// https://chatgpt.com/s/m_6a029ac6f82081918b887e6906d5141f
#include <stdio.h>
#include <stdlib.h>

union paid {
  char creditCard[21];
  int bankAccount[16];
  int iCash;
};

int main() {
  union paid moeny;
  printf("Please  enter a bank ascount number: ");
  scanf("%s", &moeny.bankAccount);
  fflush(stdin);
  printf("Bank %s\n", moeny.bankAccount);
  printf("Please  enter a credit card number: ");
  scanf("%s", &moeny.creditCard);
  fflush(stdin);
  printf("Card: %s\n", moeny.creditCard);
  printf("Please  enter an iCash amount: ");
  scanf("%d", &moeny.iCash);
  fflush(stdin);
  printf("Cash: %4d\n", moeny.iCash);
  printf("Card: %s", moeny.creditCard);
  printf("size of money is:%d\n", sizeof(moeny));
  system("pause");
  return 0;
}
