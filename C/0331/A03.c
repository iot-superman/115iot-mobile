#include<stdio.h>
#include<stdlib.h>
#define PASSWD 1234

main()
{
    int passwd;
    int flag=0,retry=1;
    do {
        printf("Please input the password:",retry++);
        scanf("%d",&passwd);
        if (passwd==PASSWD)
        {
            flag=1;
        }
    }while (!flag &&(retry<=3));

    if (flag==1)
    {
        printf("Congratulations! \n");
    }
    else
    {
        printf("You are rejectede!\n");
    }

system("pause");
return 0;
}

