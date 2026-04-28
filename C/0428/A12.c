// https://chatgpt.com/s/m_69f04c7da8508191b04277c2b19a3397

#include <stdio.h>
#include <stdlib.h>
int *maximun(int *);

int main(void) {
  int A[6] = {11, 48, 30, 17, 62, 37};
  int i,max,min;
  min=max=0;
  
  for(i=0;i<6;i++){
    if(*(A+i)>*(A+max)){
      max=i;
    }
    if(*(A+i)<*(A+min)){
      min=i;
    }
  }
  printf("The index of the maximum value in array A is %d\n",max);
  printf("The index of the minimum value in array A is %d\n",min);  
  system("pause");
  return 0;
}
