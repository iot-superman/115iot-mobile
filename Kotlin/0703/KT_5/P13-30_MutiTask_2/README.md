# P13-30_MutiTask_2

Simple Kotlin multi-threading example.

## What it does

- Creates multiple worker threads.
- Waits for all workers to finish with `CountDownLatch`.
- Prints completion messages in task order.

## Run

```powershell
Set-Location "C:\Users\User\Documents\115iot-mobile\Kotlin\0703\KT_5\P13-30_MutiTask_2"
kotlinc .\src\MUTI_Task_2.kt -include-runtime -d .\mutitask.jar
java -jar .\mutitask.jar
```
