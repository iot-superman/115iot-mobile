from django.shortcuts import render

from .forms import HousePredictionForm
from .predictor import build_prediction_frame, load_model


def home(request):
    prediction = None
    selected_district = None

    if request.method == "POST":
        form = HousePredictionForm(request.POST)
        if form.is_valid():
            prediction_frame = build_prediction_frame(form.cleaned_data)
            model = load_model()
            prediction = round(float(model.predict(prediction_frame)[0]), 2)
            selected_district = form.cleaned_data["district"]
    else:
        form = HousePredictionForm(
            initial={
                "area": 35,
                "house_age": 8,
                "room_count": 3,
                "floor": 12,
                "mrt_distance": 400,
                "school_distance": 600,
                "parking": 1,
                "district": "桃園市中壢區",
            }
        )

    return render(
        request,
        "myapp/home.html",
        {
            "form": form,
            "prediction": prediction,
            "selected_district": selected_district,
        },
    )
