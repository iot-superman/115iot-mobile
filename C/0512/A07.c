// https://chatgpt.com/s/m_6a02a22c4c208191a1b244e341df0808
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

union paid {
  char creditCard[21];
  int iCash;
};

int main() {
  union paid moeny;
  int Amt = 1500, opt;
  printf("Amounts payable: %d \n", Amt);
  do {
    printf("Chose payment methode(1)=CreateCard,(2)iCash:¡@");
    scanf("%d", &opt);
    fflush(stdin);

    if (opt == 1) {
      printf("Please enter your card number: ");
      gets(moeny.creditCard);
      if (strlen(moeny.creditCard) != 16) {
        printf("Card number error! Please re-operate!\n");
        opt = 0;
      } else {
        printf("Payment completed!\n");
      }
    } else if (opt == 2) {
      printf("Please enter the amount of cash: ");
      scanf("%d", &moeny.iCash);
      fflush(stdin);
      if (moeny.iCash < Amt) {
        printf("Insufficient amount!Full payment required!\n");
        opt = 0;
      } else {
        printf("Give change:%4d\n", moeny.iCash - Amt);
      }
    } else {
      printf("Please enter the correct payment method\n");
      opt = 0;
    }
  } while (opt != 1 && opt != 2);

  system("pause");
  return 0;
}
