#NON decorator
import time

def slow_function1():
    start_time = time.time()
    time.sleep(2) 
    print("test") 
    end_time = time.time()
    print(f"函式執行時間: {end_time - start_time:.2f} 秒")

def slow_function2():
    start_time = time.time()
    time.sleep(2) 
    print("hello world") 
    end_time = time.time()
    print(f"函式執行時間: {end_time - start_time:.2f} 秒")

slow_function1()
slow_function2()

# 缺點: 如果有多個函式需要計算執行時間，
# 每個函式都需要加 start_time 和 end_time，程式碼重複且不易維護。
