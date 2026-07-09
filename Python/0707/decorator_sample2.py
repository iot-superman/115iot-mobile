import time

#用 *args, **kwargs 可以保證
#不限制參數數量
#不限制參數名稱
#適用於所有函式
def timer_decorator(func):
    def wrapper(*args, **kwargs):
        start_time = time.time()
        result = func(*args, **kwargs) 
        end_time = time.time()
        print(f"{func.__name__} 執行時間: {end_time - start_time:.2f} 秒")
        return result
    return wrapper

#目的:將原始function增加功能
@timer_decorator
def slow_function1():
    time.sleep(2) 
    print("test1")

@timer_decorator
def slow_function2():
    time.sleep(2) 
    print("hello world2")

slow_function1()
slow_function2()

#利用decorator，可以重複應用到多個函式，不影響原始邏輯。
