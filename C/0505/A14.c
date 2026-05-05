//https://chatgpt.com/s/m_69f98b733c448191a38ddc92920d80c3
//https://gemini.google.com/share/1c9ae9ef9819
#include <stdio.h>
#include <stdlib.h>

int main(void) {

  struct time {
    int hour;
    int minute;
  };
  struct date {
    int year;
    int month;
    int day;
    struct time crt;
  } now = {2026, 5, 5, {14, 7}};

  printf("%02d/%02d/%02d %02d:%02d\n", now.month, now.day, now.year,  now.crt.hour, now.crt.minute);

  system("pause");

  return 0;
}
