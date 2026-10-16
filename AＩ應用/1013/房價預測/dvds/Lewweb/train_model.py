from myapp.predictor import MODEL_FILE_PATH, train_and_save_model


def main():
    train_and_save_model()
    print(f"模型已建立：{MODEL_FILE_PATH}")


if __name__ == "__main__":
    main()
