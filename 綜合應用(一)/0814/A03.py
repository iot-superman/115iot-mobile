# Documentation for the number guessing game
# This program uses binary search to guess a number between 0 and 7 that the user thinks of
# It asks three questions to check if the number appears in specific sets, and calculates the result based on user's answers

ans = 0
print("請猜0-7的一個數字：")
# First question checks the least significant bit (1's place: 1,3,5,7)
q1 = "請問有看到心中想的數字嗎： \n" + \
    "1, 3, 5, 7\n"
tf = "輸入y或Y代表有，其他為無： "
num = input(q1 + tf)
print(num)
if num.lower() == 'y':
    ans += 1
# Second question checks the second bit (2's place: 2,3,6,7)
q2 = "請問有看到心中想的數字嗎： \n" + \
    "2, 3, 6, 7\n"
num=input(q2+tf)
print(num)
if num.lower() == 'y':
    ans += 2
# Third question checks the most significant bit (4's place: 4,5,6,7)
q3 = "請問有看到心中想的數字嗎： \n" + \
    "4, 5, 6, 7\n"
num = input(q3 + tf)
print(num)
if num.lower() == 'y':
    ans += 4
# Output the final guessed number
print("你心中想的數字是：", ans)
