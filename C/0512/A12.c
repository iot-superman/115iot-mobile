//https://chatgpt.com/s/m_6a02c90f5fac8191a202cccd915340ea
#include <stdio.h>
#include <stdlib.h>

int main() {
  enum week { sun, mon, tue, wed, thu, fri, sat } day;
  int total = 0, pay, hour;
  printf("Please enter your working hours from Sunday to Saturday\n");
  for (day = sun; day <= sat; day++) {
    scanf("%d", &hour);
    fflush(stdin);
    switch (day) {
    case sun:
      pay = hour * 260;
      break;
    case sat:
      pay = hour * 230;
      break;
    default:
      pay = hour * 196;
      break;
    }
    total += pay;
  }
  printf("The weekly salary is: %d\n", total);

  system("pause");
  return 0;
}
