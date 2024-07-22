from django import forms

from analyzer.models import City


class CityCreateForm(forms.ModelForm):
    class Meta:
        model = City
        fields = ["name"]


class CityUpdateForm(forms.ModelForm):
    class Meta:
        model = City
        fields = ["name"]


class CityDeleteForm(forms.Form):
    city_id = forms.IntegerField(widget=forms.HiddenInput())
