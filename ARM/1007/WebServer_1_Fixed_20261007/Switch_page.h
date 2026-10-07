#pragma once
#include <pgmspace.h>
// 原始 Switch_page.h 未提供，以下重建相容的表單頁，POST /switch 欄位 led/state。
static const char Switch_page[] PROGMEM = R"HTMLPAGE(
<!doctype html><html lang="zh-Hant"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>LED Switch</title>
<style>body{font-family:Arial,sans-serif;max-width:520px;margin:30px auto;padding:16px}button,select{padding:12px;margin:5px;border-radius:8px}button{background:#2563eb;color:white;border:0}</style></head><body><h2>LED Switch 控制</h2><form method="post" action="/switch"><label>LED <select name="led"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select></label><label>狀態 <select name="state"><option value="on">ON</option><option value="off">OFF</option></select></label><button type="submit">送出</button></form><p><a href="/">返回首頁</a></p></body></html>
)HTMLPAGE";
