let access = document.getElementById("code5");  // 1. 選取元素
let code = access.innerHTML;                    // 2. 讀取內容
code = code + " , and catcatch the mouse";                      // 3. 改變變數 (但 DOM 沒變!)
alert(code);                                    // 4. 顯示變數內容                                              
