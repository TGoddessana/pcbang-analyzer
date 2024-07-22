from django.forms import ModelForm

from analyzer.models import City


class CityForm(ModelForm):
    class Meta:
        model = City
        fields = ["name"]
