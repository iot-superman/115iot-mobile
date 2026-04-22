//https://chatgpt.com/s/t_69e83c771d708191b81bec6a6a86f3f2
#include <stdio.h>
#include <stdlib.h>

int func(int b[], int n);
int i;  // 办跑计瘤礛ノぃ某

int main(void) {
    int a[10] = {1,3,9,2,5,8,4,9,6,7};

    printf("%d\n", func(a, 10));

    system("pause");
    return 0;
}



/* ㄧΑ func皚 b いт程ま
   把计
     b[] - 俱计皚
     n   - 皚
   肚
     皚い程材Ω瞷ま
*/
int func(int b[], int n)
{
    int index = 0;          // 安砞材 0 じ琌ヘ玡程

    for(i = 1; i <= n - 1; i++)
    {
        // ? タ场эΘ b[]
        if(b[i] >= b[index])
        {
            index = i;      // т┪单穝ま
        }
    }

    return index;           // 肚程┮ま
}
