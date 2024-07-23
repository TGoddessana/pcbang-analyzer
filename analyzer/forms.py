from django import forms

from analyzer.models import City, Pcbang


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


class PcbangCreateForm(forms.ModelForm):
    class Meta:
        model = Pcbang
        fields = "__all__"


class PcbangUpdateForm(forms.ModelForm):
    class Meta:
        model = Pcbang
        fields = "__all__"


class PcbangDeleteForm(forms.ModelForm):
    class Meta:
        model = Pcbang
        fields = []
