def calculate_bmi(height, weight):
    bmi = weight / (height ** 2)
    return bmi


def main():
    # call the function
    height = float(input("請輸入身高(公尺)："))
    weight = float(input("請輸入體重(公斤)："))

    bmi = calculate_bmi(height, weight)

    print(f"您的BMI值為：{bmi:.2f}")


if __name__ == "__main__":
    main()