/* 
  3.請撰寫一程式，可輸入三個不同整數，然後 if…else if 找出這三個數值哪個最大
*/
/*
https://chatgpt.com/share/69b8a280-ce04-8010-8231-78da6ea6f764
你的想法 **接近數學概念，但在程式邏輯上不成立**。
原因是 **你誤把「遞移律（Transitive property）」用在錯誤的判斷情境**。

---

# 1 數學上的遞移律是什麼

數學的遞移律：

如果

[
a > b \quad 且 \quad b > c
]

則必然成立

[
a > c
]

例如：

```
a = 10
b = 7
c = 3
```

成立：

```
10 > 7
7 > 3
所以 10 > 3
```

這就是 **遞移律**。

---

# 2 但你的程式問題在哪

你的條件：

```c
if (a>b && b>c)
```

只代表 **一種特殊情況**：

```
a > b > c
```

也就是 **嚴格排序**

```
a 最大
b 第二
c 最小
```

但題目只要求：

```
找最大值
```

而不是要求 **排序**。

---

# 3 反例（你的程式會錯）

例如：

```
a = 9
b = 1
c = 5
```

最大是 `a`

但你的條件：

```
a>b → true
b>c → false
```

整個條件變成：

```
true && false = false
```

所以程式不會判斷 `a` 最大。

---

# 4 正確邏輯

找最大值應該寫：

```
a > b 且 a > c
```

也就是：

```c
if (a>b && a>c)
```

完整邏輯：

```c
if (a>b && a>c)
    a最大
else if (b>a && b>c)
    b最大
else
    c最大
```

---

# 5 三個數大小的所有排列

三個數其實有 **6種排列**

```
a>b>c
a>c>b
b>a>c
b>c>a
c>a>b
c>b>a
```

但你的程式只抓到：

```
a>b>c
```

所以會漏掉很多情況。

---

# 6 結論

你的想法：

```
a>b && b>c
```

數學上 **遞移律成立**

但程式邏輯：

```
判斷最大值
```

就必須寫：

```
a>b && a>c
```

---



*/
#include <stdlib.h>
#include <stdio.h>

int main(void)
{
   int a, b,c ;
   printf("輸入整數a: ");
   scanf("%d", &a);
   printf("輸入整數b: ");
   scanf("%d", &b);
   printf("輸入整數c: ");
   scanf("%d", &c);
   
   if (a>b && a>c){
   		printf("a");
   } else if (b>c && b>c){
   		printf("b");
   }else{
   		printf("c");
   }
   
   
    

	
   printf("值最大\n");
   system("pause");
   return 0;
}
