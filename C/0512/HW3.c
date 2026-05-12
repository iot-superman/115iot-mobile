/* 

3.  試建立列舉  card 內有成員 Platinum / Gold / Silver,  分別可以享有會員折扣  8 折  / 8.5 折  / 9
折,  輸入會員消費金額及卡別,  螢幕輸出結帳金額。 
float money; 
        enum card 
        { 
           
  } mycard; 

*/
enum card {
  Platinum=1,
  Gold  ,
  Silver 
};

#include <stdio.h>
#include <stdlib.h>

int main() {
  float money;
  float discount = 1.0f;  
  int inputCardLevel=0;
  
  printf("Please input the money ? ");
  scanf("%f", &money);
  printf("Please input the card(1-3) (1:Platinum,2:Gold,3:Silver): ");
  scanf("%d", &inputCardLevel);
  
    switch(inputCardLevel) {

        case Platinum:
            discount = 0.8f;     // 8折 
            break;

        case Gold:
            discount = 0.85f;    // 85折 
            break;

        case Silver:
            discount = 0.9f;     // 9折 
            break;

        default:
            printf("Card Error!\n");
            system("pause");
            return 0;
    }

    printf("The discount is %.0f%%.\n", discount * 100);

    printf("The final price is %.2f\n",
           money * discount);

  return 0;
}
