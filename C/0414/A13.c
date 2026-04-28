#include <stdio.h>
#include <stdlib.h>
/*建立C語言的函數*/
float sweight(float , int);

int main(void) {
  int sex;
  float height;
  float weight;
  do {
    printf("請輸入性別(0:female女 1:Male男):");
    scanf("%d",&sex);
    fflush(stdin);
    if(sex != 0 && sex != 1) {
      printf("輸入錯誤，請重新輸入！\n");
    }
  } while(sex != 0 && sex != 1);
  printf("請輸入身高(公分):");
  scanf("%f",&height);
  fflush(stdin);
  weight=sweight(height,sex);
  printf("您是%s的標準體重應為:%f公斤\n",sex==0?"女性":"男性",weight);
  

  system("pause");
  return 0;
}

float sweight(float height, int sex) {
  float ret;

  if (sex == 0) {
    ret = (height - 70) * 0.6;     // 女性體重公式
  } else {
    ret = (height - 80) * 0.7;    // 男性體重公式
  }
  return ret;
}
 
