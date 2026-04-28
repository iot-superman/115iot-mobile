#include <stdio.h>
#include <string.h>

int main(void)
{
  char str[50];
  int i;

  printf("請輸入字串 (+加密 / -解密)：\n");
  scanf("%s", str);   // ? 不要加 &

  // =========================
  // 加密：+ 開頭
  // =========================
  if (str[0] == '+')
  {
    for (i = 1; i < strlen(str); i++)
    {
      // ? 小寫字母
      if (str[i] >= 'a' && str[i] <= 'z')
      {
        str[i] = str[i] + 2;

        if (str[i] > 'z')
        {
          str[i] = str[i] - 26;  // ? 回繞
        }
      }
      // ? 大寫字母
      else if (str[i] >= 'A' && str[i] <= 'Z')
      {
        str[i] = str[i] + 2;

        if (str[i] > 'Z')
        {
          str[i] = str[i] - 26;
        }
      }
    }
  }

  // =========================
  // 解密：- 開頭
  // =========================
  if (str[0] == '-')
  {
    for (i = 1; i < strlen(str); i++)
    {
      // ? 小寫
      if (str[i] >= 'a' && str[i] <= 'z')
      {
        str[i] = str[i] - 2;

        if (str[i] < 'a')
        {
          str[i] = str[i] + 26;
        }
      }
      // ? 大寫
      else if (str[i] >= 'A' && str[i] <= 'Z')
      {
        str[i] = str[i] - 2;

        if (str[i] < 'A')
        {
          str[i] = str[i] + 26;
        }
      }
    }
  }

  printf("結果：%s\n", str);

  return 0;
}