#include <stdio.h>
#include <stdlib.h>

int main(void) {

    char *ptr = "We are best friends.";
    int i, count = 0;

   
    for (i = 0; *(ptr + i) != '\0'; i++) {

       
        if (*(ptr + i) >= 97 && *(ptr + i) <= 122) {
            count = count + 1;   
        }
    }
   
    printf("Number of lowercase letters is %d\n", count);

   
    system("pause");

    return 0;
}
