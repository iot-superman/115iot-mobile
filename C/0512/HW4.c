/*/
4.  定義  union  和  struct，都含  int, float, char，並比較其占用空間大小。 
        myUnion 
        int      math 
        float    sum 
        char    id 

*/
#include <stdio.h>
#include <stdlib.h>

union myUnion
{
    int math;
    float sum;
    char id;
};

struct myStruct
{
    int math;
    float sum;
    char id;
};



int main() {
    union myUnion u;
    struct myStruct s;

    printf("Size of union  : %d bytes\n", sizeof(u));
    printf("Size of struct : %d bytes\n", sizeof(s));

     
  system("pause");
  return 0;
}
