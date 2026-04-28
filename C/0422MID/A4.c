/*
 

4.

試撰寫工讀生的月薪資，請依照下列方式計算：

40 小時內：每小時 170 元
40～80 小時：1.25 倍
80 小時以上：1.5 倍

 
*/
 
#include <stdio.h>
#include <stdlib.h>

#define HOURPAY 170.0
float salary(float); 

int main(void) {
    float hours=0.0;
    //43
    printf("Please input hours:",hours);
    scanf("%f",&hours);
    fflush(stdin);
    printf("Your salary is $%.2f\n",salary(hours));


    system("pause");
    return 0;
}


float salary(float hours) {
    float result=0.0;   
    if(hours<=40){    //<=40       
        result=hours*HOURPAY;   
    }
    else if(hours<80){  //41 -79                            
        result=40*HOURPAY + (hours-40)*HOURPAY*1.25;
    }
    else{   //80以上小時
     
              result = 40 * HOURPAY +               
                 40 * HOURPAY * 1.25 +               
                 (hours - 80) * HOURPAY * 1.5;       
    }
    return result;   
}










