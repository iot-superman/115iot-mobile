//
// Created by User on 2026/3/31.
//
#include <stdio.h>
#include <stdlib.h>

 
 
int main(void) {
   
    int B[2][4][3]={{
                    {21,32,65},
                    {78,95,76},
                    {81,44,65},
                    {89,54,73}
                      },
                    {
                       {33,56,88},
                       {43,88,99},
                       {31,57,77},
                      {94,72,45}
                      }
                     };
      //print 77
//    printf("77:location: B[0][2][2] = %d\n", B[1][2][2]);

     int i,j,k;
    int max=B[0][0][0];
 
    for (i=0; i<2; i++) {
        for (j=0; j<4; j++) {
            for (k=0; k<3; k++) {
                if (max<B[i][j][k]) {
                    max=B[i][j][k];
                }
            }
        }
    }
    printf("max=%d\n",max);
 
    system("pause");

  
    return 0;
}
