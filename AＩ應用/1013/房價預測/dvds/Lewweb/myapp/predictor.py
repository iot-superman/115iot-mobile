from pathlib import Path

import joblib
import pandas as pd
from sklearn.linear_model import LinearRegression

NUMERIC_FEATURE_COLUMNS = [
    "坪數",
    "屋齡",
    "房間數",
    "樓層",
    "捷運距離公尺",
    "學校距離公尺",
    "有車位",
]

DISTRICT_NAMES = [
    "台北市信義區",
    "台北市大安區",
    "台南市東區",
    "新北市新店區",
    "新北市板橋區",
    "桃園市中壢區",
    "高雄市左營區",
]

DISTRICT_COLUMNS = [f"行政區_{district}" for district in DISTRICT_NAMES]
FEATURE_COLUMNS = NUMERIC_FEATURE_COLUMNS + DISTRICT_COLUMNS
TARGET_COLUMN = "房價萬元"
BASE_DIR = Path(__file__).resolve().parent.parent
DATA_FILE_PATH = BASE_DIR / "static" / "LinearRegressionData.csv"
MODEL_FILE_PATH = BASE_DIR / "static" / "LinearRegressionModel.pkl"


def build_training_features(dataframe):
    filtered_dataframe = dataframe[dataframe["行政區"].isin(DISTRICT_NAMES)].copy()
    if filtered_dataframe.empty:
        raise ValueError("找不到可用來訓練模型的行政區資料。")

    numeric_features = filtered_dataframe[NUMERIC_FEATURE_COLUMNS].copy()
    district_features = pd.get_dummies(
        filtered_dataframe["行政區"],
        prefix="行政區",
        dtype=int,
    ).reindex(columns=DISTRICT_COLUMNS, fill_value=0)
    return pd.concat([numeric_features, district_features], axis=1)


def train_and_save_model():
    dataframe = pd.read_csv(DATA_FILE_PATH, encoding="utf-8-sig")
    training_features = build_training_features(dataframe)
    training_target = dataframe.loc[training_features.index, TARGET_COLUMN]

    model = LinearRegression()
    model.fit(training_features, training_target)
    joblib.dump(model, MODEL_FILE_PATH)
    return model


def load_model():
    if not MODEL_FILE_PATH.exists():
        raise FileNotFoundError(f"找不到模型檔案：{MODEL_FILE_PATH}")
    return joblib.load(MODEL_FILE_PATH)


def build_prediction_frame(cleaned_data):
    row_data = {column: 0 for column in FEATURE_COLUMNS}
    row_data["坪數"] = float(cleaned_data["area"])
    row_data["屋齡"] = int(cleaned_data["house_age"])
    row_data["房間數"] = int(cleaned_data["room_count"])
    row_data["樓層"] = int(cleaned_data["floor"])
    row_data["捷運距離公尺"] = int(cleaned_data["mrt_distance"])
    row_data["學校距離公尺"] = int(cleaned_data["school_distance"])
    row_data["有車位"] = int(cleaned_data["parking"])
    row_data[f"行政區_{cleaned_data['district']}"] = 1
    return pd.DataFrame([row_data], columns=FEATURE_COLUMNS)
