#include <stdlib.h>
#include <stdio.h>
void show(int[]);
void peri(double);
void area(double);
const double pi= 3.14;

int main(void) {
  double r=1.0;
  printf("pi=%.2f \n",pi);
  printf("radius=%.2f\n",r);
  peri(r);
  area(r);
  system("pause");
  return 0;
  }

void peri(double r){
  printf("Circumferenece=%.2f\n",2 *pi *r);
}
void area(double r){
  printf("Circlaur area=%.2f\n", pi*r*r);
}
