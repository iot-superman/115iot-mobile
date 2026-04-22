#include <stdlib.h>
#include <stdio.h>
double get_score(void);
char level(double,double,double);
int main(void)
{
    double s1,s2,s3;
    char grade;
    s1=get_score();
    s2=get_score();
    s3=get_score();
    grade=level(s1,s2,s3);
    printf("Your grade is %c\n",grade);
    system("pause");
    return 0;
}

double get_score(void)
{
    double score;
    printf("Please input your score:");
    scanf("%lf",&score);
    return score;
}

char level(double s1,double s2,double s3)
{
    double avg;
    printf("Your average score  %.1f %.1f %.1f\n",s1,s2,s3);
    avg=(s1+s2+s3)/3;
    printf("Average: %4.1f\n",avg);
    if(avg>=90)
    {
        return 'A';
    }
    else if(avg>=80)
    {
        return 'B';
    }
    else if(avg>=70)
    {
        return 'C';
    }
    else if(avg>=60)
    {
        return 'D';
    }
    else
    {
        return 'E';
    }
}