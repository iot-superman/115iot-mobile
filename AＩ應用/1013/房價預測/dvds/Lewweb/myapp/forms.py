from django import forms

from .predictor import DISTRICT_NAMES


class HousePredictionForm(forms.Form):
    area = forms.DecimalField(label="坪數", min_value=0, decimal_places=1, max_digits=6)
    house_age = forms.IntegerField(label="屋齡", min_value=0)
    room_count = forms.IntegerField(label="房間數", min_value=1)
    floor = forms.IntegerField(label="樓層", min_value=1)
    mrt_distance = forms.IntegerField(label="捷運距離公尺", min_value=0)
    school_distance = forms.IntegerField(label="學校距離公尺", min_value=0)
    parking = forms.TypedChoiceField(
        label="有車位",
        choices=((1, "有"), (0, "沒有")),
        coerce=int,
    )
    district = forms.ChoiceField(
        label="行政區",
        choices=[(district, district) for district in DISTRICT_NAMES],
    )
