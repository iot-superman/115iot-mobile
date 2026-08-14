import random
the_Day = "Friday"
the_day = "星期五"
print("Today is {1}  今天是{0}".format(the_day, the_Day))


def get_day():
    return random.choice(["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"])

print("今天是", get_day())