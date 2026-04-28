#include <stdio.h>
#include <stdlib.h>

int main(void){
    float a, b;
    char op;

    printf("Please input the expression (ex: 3+2):\n");
    scanf("%f %c %f", &a, &op, &b);

    printf("%.1f %c %.1f", a, op, b);

    switch(op){
        case '+':
            printf(" = %.1f\n", a + b);
            break;
        case '-':
            printf(" = %.1f\n", a - b);
            break;
        case '*':
            printf(" = %.1f\n", a * b);
            break;
        case '/':
            printf(" = %.1f\n", a / b);
            break;
        default:
            printf(" input error!\n");
    }

    return 0;
}
