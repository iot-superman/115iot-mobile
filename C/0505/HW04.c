/*
依據結構完成程式,  並顯示城市：Taoyuan，行政區：Yangmei，郵遞區號：326023。
struct Person {
        char city[30];
        struct area addr;
};
struct Person tom = {"Taoyuan", {"Yangmei", 326023}};
*/
#include <stdio.h>
#include <stdlib.h>

int main(void) {
  struct Person {
    char city[30];
    struct area {
      char district[30];
      int zip;
    } addr;
  };
  struct Person tom = {"Taoyuan", {"Yangmei", 326023}};
  printf("City: %s\n", tom.city);
  printf("District: %s\n", tom.addr.district);
  printf("Zip: %d\n", tom.addr.zip);

  system("pause");
  return 0;
}
