/*
 

5.


9
98
987
9876
98765
987654
9876543
98765432
987654321
 
*/
 
#include <stdio.h>
#include <stdlib.h>

int main(void) {
    int i, j;
    
    for (i = 9; i >= 1; i--) {
       
        for (j = 9; j >= i; j--) {
            printf("%d", j);
        }
        
        printf("\n");
    }
    system("pause");
    return 0;
}

 
 
