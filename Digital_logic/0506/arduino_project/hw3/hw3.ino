/*
  隨堂練習 3：進位轉換 Serial.print 過程版

  使用方式：
  1. 上傳到 Arduino
  2. 開啟 Serial Monitor
  3. 鮑率選 115200
*/

void setup() {
  Serial.begin(115200);

  delay(500);

  Serial.println("====================================");
  Serial.println("隨堂練習 3：進位轉換解題過程");
  Serial.println("====================================");
  Serial.println();

  question1();
  question2();
  question3();
  question4();
  question5();
  question6();
  question7();
  question8();
  question9();
  question10();

  Serial.println("====================================");
  Serial.println("全部題目完成");
  Serial.println("====================================");
}

void loop() {
  // 不需要重複執行，所以 loop 留空
}

/* --------------------------------------------------
   第 1 題
   1101(B) = ?(H)
-------------------------------------------------- */
void question1() {
  Serial.println("1. 1101(B) = ?(H)");
  Serial.println();

  Serial.println("二進位轉十六進位：每 4 個 bit 分成一組");
  Serial.println("1101");
  Serial.println();

  Serial.println("1101(B) = D(H)");
  Serial.println();

  Serial.println("答案：D(H)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 2 題
   100110(B) = ?(H)
-------------------------------------------------- */
void question2() {
  Serial.println("2. 100110(B) = ?(H)");
  Serial.println();

  Serial.println("二進位轉十六進位：每 4 個 bit 分成一組");
  Serial.println("原本：100110");
  Serial.println("從右邊開始每 4 位分組，不足 4 位左邊補 0");
  Serial.println();

  Serial.println("100110");
  Serial.println("= 0010 0110");
  Serial.println();

  Serial.println("0010(B) = 2(H)");
  Serial.println("0110(B) = 6(H)");
  Serial.println();

  Serial.println("所以：100110(B) = 26(H)");
  Serial.println();

  Serial.println("答案：26(H)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 3 題
   1101001.101(B) = ?(H)
-------------------------------------------------- */
void question3() {
  Serial.println("3. 1101001.101(B) = ?(H)");
  Serial.println();

  Serial.println("二進位轉十六進位：每 4 個 bit 分成一組");
  Serial.println();

  Serial.println("整數部分：1101001");
  Serial.println("從小數點往左分組，不足 4 位左邊補 0");
  Serial.println("1101001 = 0110 1001");
  Serial.println();

  Serial.println("0110(B) = 6(H)");
  Serial.println("1001(B) = 9(H)");
  Serial.println("整數部分 = 69(H)");
  Serial.println();

  Serial.println("小數部分：.101");
  Serial.println("從小數點往右分組，不足 4 位右邊補 0");
  Serial.println(".101 = .1010");
  Serial.println();

  Serial.println("1010(B) = A(H)");
  Serial.println("小數部分 = .A(H)");
  Serial.println();

  Serial.println("所以：1101001.101(B) = 69.A(H)");
  Serial.println();

  Serial.println("答案：69.A(H)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 4 題
   4.5(D) = ?(B)
-------------------------------------------------- */
void question4() {
  Serial.println("4. 4.5(D) = ?(B)");
  Serial.println();

  Serial.println("十進位轉二進位，要分成整數部分與小數部分處理");
  Serial.println();

  Serial.println("整數部分：4");
  Serial.println("4 / 2 = 2 ... 0");
  Serial.println("2 / 2 = 1 ... 0");
  Serial.println("1 / 2 = 0 ... 1");
  Serial.println("餘數由下往上讀：100");
  Serial.println();

  Serial.println("小數部分：0.5");
  Serial.println("0.5 * 2 = 1.0");
  Serial.println("取整數部分：1");
  Serial.println();

  Serial.println("所以：4.5(D) = 100.1(B)");
  Serial.println();

  Serial.println("答案：100.1(B)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 5 題
   2A5.D(H) = ?(B)
-------------------------------------------------- */
void question5() {
  Serial.println("5. 2A5.D(H) = ?(B)");
  Serial.println();

  Serial.println("十六進位轉二進位：每 1 個 Hex 數字轉成 4 個 bit");
  Serial.println();

  Serial.println("整數部分：2A5");
  Serial.println("2(H) = 0010(B)");
  Serial.println("A(H) = 1010(B)");
  Serial.println("5(H) = 0101(B)");
  Serial.println();

  Serial.println("小數部分：.D");
  Serial.println("D(H) = 1101(B)");
  Serial.println();

  Serial.println("所以：2A5.D(H)");
  Serial.println("= 0010 1010 0101 . 1101(B)");
  Serial.println();

  Serial.println("答案：001010100101.1101(B)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 6 題
   7E6.18(H) = ?(B)
-------------------------------------------------- */
void question6() {
  Serial.println("6. 7E6.18(H) = ?(B)");
  Serial.println();

  Serial.println("十六進位轉二進位：每 1 個 Hex 數字轉成 4 個 bit");
  Serial.println();

  Serial.println("整數部分：7E6");
  Serial.println("7(H) = 0111(B)");
  Serial.println("E(H) = 1110(B)");
  Serial.println("6(H) = 0110(B)");
  Serial.println();

  Serial.println("小數部分：.18");
  Serial.println("1(H) = 0001(B)");
  Serial.println("8(H) = 1000(B)");
  Serial.println();

  Serial.println("所以：7E6.18(H)");
  Serial.println("= 0111 1110 0110 . 0001 1000(B)");
  Serial.println();

  Serial.println("答案：011111100110.00011000(B)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 7 題
   187(D) = ?(H)
-------------------------------------------------- */
void question7() {
  Serial.println("7. 187(D) = ?(H)");
  Serial.println();

  Serial.println("十進位轉十六進位：一直除以 16，取餘數");
  Serial.println();

  Serial.println("187 / 16 = 11 ... 11");
  Serial.println("11(D) = B(H)");
  Serial.println();

  Serial.println("商數 11 再繼續除：");
  Serial.println("11 / 16 = 0 ... 11");
  Serial.println("11(D) = B(H)");
  Serial.println();

  Serial.println("餘數由下往上讀：BB");
  Serial.println();

  Serial.println("所以：187(D) = BB(H)");
  Serial.println();

  Serial.println("答案：BB(H)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 8 題
   37.6(O) = ?(H)
-------------------------------------------------- */
void question8() {
  Serial.println("8. 37.6(O) = ?(H)");
  Serial.println();

  Serial.println("八進位轉十六進位：通常先轉二進位，再轉十六進位");
  Serial.println();

  Serial.println("八進位轉二進位：每 1 個 Oct 數字轉成 3 個 bit");
  Serial.println();

  Serial.println("整數部分：37(O)");
  Serial.println("3(O) = 011(B)");
  Serial.println("7(O) = 111(B)");
  Serial.println("所以整數部分：011111(B)");
  Serial.println();

  Serial.println("小數部分：.6(O)");
  Serial.println("6(O) = 110(B)");
  Serial.println("所以小數部分：.110(B)");
  Serial.println();

  Serial.println("合起來：37.6(O) = 011111.110(B)");
  Serial.println();

  Serial.println("二進位轉十六進位：每 4 個 bit 分成一組");
  Serial.println();

  Serial.println("整數部分：011111");
  Serial.println("從小數點往左分組，不足 4 位左邊補 0");
  Serial.println("011111 = 0001 1111");
  Serial.println("0001(B) = 1(H)");
  Serial.println("1111(B) = F(H)");
  Serial.println("整數部分 = 1F(H)");
  Serial.println();

  Serial.println("小數部分：.110");
  Serial.println("從小數點往右分組，不足 4 位右邊補 0");
  Serial.println(".110 = .1100");
  Serial.println("1100(B) = C(H)");
  Serial.println("小數部分 = .C(H)");
  Serial.println();

  Serial.println("所以：37.6(O) = 1F.C(H)");
  Serial.println();

  Serial.println("答案：1F.C(H)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 9 題
   5046.452(O) = ?(H)
-------------------------------------------------- */
void question9() {
  Serial.println("9. 5046.452(O) = ?(H)");
  Serial.println();

  Serial.println("八進位轉十六進位：通常先轉二進位，再轉十六進位");
  Serial.println();

  Serial.println("八進位轉二進位：每 1 個 Oct 數字轉成 3 個 bit");
  Serial.println();

  Serial.println("整數部分：5046(O)");
  Serial.println("5(O) = 101(B)");
  Serial.println("0(O) = 000(B)");
  Serial.println("4(O) = 100(B)");
  Serial.println("6(O) = 110(B)");
  Serial.println();

  Serial.println("整數部分合併：");
  Serial.println("5046(O) = 101 000 100 110(B)");
  Serial.println("也就是：101000100110(B)");
  Serial.println();

  Serial.println("小數部分：.452(O)");
  Serial.println("4(O) = 100(B)");
  Serial.println("5(O) = 101(B)");
  Serial.println("2(O) = 010(B)");
  Serial.println();

  Serial.println("小數部分合併：");
  Serial.println(".452(O) = .100 101 010(B)");
  Serial.println("也就是：.100101010(B)");
  Serial.println();

  Serial.println("所以：");
  Serial.println("5046.452(O) = 101000100110.100101010(B)");
  Serial.println();

  Serial.println("二進位轉十六進位：每 4 個 bit 分成一組");
  Serial.println();

  Serial.println("整數部分：101000100110");
  Serial.println("從小數點往左分組：");
  Serial.println("101000100110 = 1010 0010 0110");
  Serial.println();

  Serial.println("1010(B) = A(H)");
  Serial.println("0010(B) = 2(H)");
  Serial.println("0110(B) = 6(H)");
  Serial.println("整數部分 = A26(H)");
  Serial.println();

  Serial.println("小數部分：.100101010");
  Serial.println("從小數點往右分組，不足 4 位右邊補 0：");
  Serial.println(".100101010 = .1001 0101 0000");
  Serial.println();

  Serial.println("1001(B) = 9(H)");
  Serial.println("0101(B) = 5(H)");
  Serial.println("0000(B) = 0(H)");
  Serial.println("小數部分 = .950(H)");
  Serial.println();

  Serial.println("所以：5046.452(O) = A26.950(H)");
  Serial.println();

  Serial.println("答案：A26.950(H)");
  Serial.println("------------------------------------");
  Serial.println();
}

/* --------------------------------------------------
   第 10 題
   6F.4(H) = ?(O)
-------------------------------------------------- */
void question10() {
  Serial.println("10. 6F.4(H) = ?(O)");
  Serial.println();

  Serial.println("十六進位轉八進位：通常先轉二進位，再轉八進位");
  Serial.println();

  Serial.println("十六進位轉二進位：每 1 個 Hex 數字轉成 4 個 bit");
  Serial.println();

  Serial.println("整數部分：6F(H)");
  Serial.println("6(H) = 0110(B)");
  Serial.println("F(H) = 1111(B)");
  Serial.println("整數部分 = 01101111(B)");
  Serial.println();

  Serial.println("小數部分：.4(H)");
  Serial.println("4(H) = 0100(B)");
  Serial.println("小數部分 = .0100(B)");
  Serial.println();

  Serial.println("所以：6F.4(H) = 01101111.0100(B)");
  Serial.println();

  Serial.println("二進位轉八進位：每 3 個 bit 分成一組");
  Serial.println();

  Serial.println("整數部分：01101111");
  Serial.println("從小數點往左分組，不足 3 位左邊補 0：");
  Serial.println("01101111 = 001 101 111");
  Serial.println();

  Serial.println("001(B) = 1(O)");
  Serial.println("101(B) = 5(O)");
  Serial.println("111(B) = 7(O)");
  Serial.println("整數部分 = 157(O)");
  Serial.println();

  Serial.println("小數部分：.0100");
  Serial.println("從小數點往右分組，不足 3 位右邊補 0：");
  Serial.println(".0100 = .010 000");
  Serial.println();

  Serial.println("010(B) = 2(O)");
  Serial.println("000(B) = 0(O)");
  Serial.println("小數部分 = .20(O)");
  Serial.println();

  Serial.println("尾端的 0 可以省略：.20(O) = .2(O)");
  Serial.println();

  Serial.println("所以：6F.4(H) = 157.2(O)");
  Serial.println();

  Serial.println("答案：157.2(O)");
  Serial.println("------------------------------------");
  Serial.println();
}
