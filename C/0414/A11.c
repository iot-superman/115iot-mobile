#include <stdio.h>
#include <stdlib.h>
/*ミC粂ēㄧ计*/

//float碞ノ 
float power(float,int); 

int main(void)
{
 float x;
 int n;
 printf("叫块2计 ex:(膀计 计):");
 scanf("%f %d",&x,&n);
 fflush(stdin);
 printf("%.1f%dΩよ=%.1f\n",x,n,power(x,n)); //f 疊翴计,
//  printf("%.1f%dΩよ=%.1l\n",x,n,power(x,n)); //l 疊翴计,lf琌double 

}

int abs(int a){
    if(a<0){
        return -a;
    }
    return a;
}

float power(float x,int n){
    int i;
    float pow=1.0f;
    for(i=0;i<n;i++){
        pow=pow*x;
    }
    return pow;
}
