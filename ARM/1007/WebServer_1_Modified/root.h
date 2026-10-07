#pragma once
#include <pgmspace.h>
// 重建的可獨立使用首頁：原始 root.h 未隨貼文上傳，故非老師網頁的逐字版本。
static const char root_page[] PROGMEM = R"HTMLPAGE(
<!doctype html><html lang="zh-Hant"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>NodeMCU 五燈控制</title>
<style>body{font-family:Arial,'Noto Sans TC',sans-serif;background:#f5f7fb;color:#243047;max-width:680px;margin:24px auto;padding:0 14px}h1{font-size:25px}.card{background:white;padding:18px;border-radius:14px;box-shadow:0 2px 8px #0001;margin:12px 0}.led{display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid #eee;padding:10px 0}.led:last-child{border:0}button{border:0;border-radius:8px;padding:11px 17px;margin:4px;cursor:pointer;font-weight:bold;background:#176acf;color:white}button.off{background:#64748b}button.flash{background:#df6200;font-size:18px}.state{font-weight:bold;color:#64748b}.hint{font-size:13px;color:#64748b}#message{min-height:24px;white-space:pre-wrap}</style></head><body>
<h1>NodeMCU LED 網頁控制</h1><div class="card"><p>LED1 = D4 (GPIO2)，LED2~5 = GPIO14/12/13/15</p><button class="flash" onclick="action('/flash')">⚡ FLASH 全燈閃爍 3 次</button><button onclick="refresh()">更新狀態</button><p id="message" role="status"></p></div>
<div class="card" id="leds"></div><div class="card"><a href="/switch">開啟老師原版 Switch 相容頁</a><p class="hint">路由：/on、/off、/on/1～5、/off/1～5、/ledon?led=3、/ledoff?led=3、/flash、/status</p></div>
<script>
async function action(path){try{let r=await fetch(path);let s=await r.text();document.getElementById('message').textContent=s;if(path==='/flash'){setTimeout(refresh,1900)}else{await refresh()}}catch(e){document.getElementById('message').textContent='連線失敗：'+e}}
async function refresh(){try{let s=await (await fetch('/status',{cache:'no-store'})).json();document.getElementById('leds').innerHTML=s.leds.map((on,i)=>`<div class="led"><div><strong>LED ${i+1}</strong> <span class="state">${on?'亮':'暗'}</span></div><div><button onclick="action('/on/${i+1}')">ON</button><button class="off" onclick="action('/off/${i+1}')">OFF</button></div></div>`).join('')}catch(e){document.getElementById('message').textContent='狀態讀取失敗：'+e}}
refresh();
</script></body></html>
)HTMLPAGE";
