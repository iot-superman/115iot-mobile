from django.test import TestCase
from django.urls import reverse

from .predictor import MODEL_FILE_PATH, load_model


class HomeViewTests(TestCase):
    @classmethod
    def setUpClass(cls):
        super().setUpClass()
        load_model()

    def test_home_page_loads(self):
        response = self.client.get(reverse("home"))

        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "房價預測網站")

    def test_existing_model_file_is_available(self):
        self.assertTrue(MODEL_FILE_PATH.exists())

    def test_prediction_result_is_displayed(self):
        response = self.client.post(
            reverse("home"),
            data={
                "area": 35,
                "house_age": 8,
                "room_count": 3,
                "floor": 12,
                "mrt_distance": 400,
                "school_distance": 600,
                "parking": 1,
                "district": "桃園市中壢區",
            },
        )

        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "預測房價")
