from django.shortcuts import render
from django.views.generic import ListView

from analyzer.forms import CityCreateForm, CityUpdateForm, CityDeleteForm
from analyzer.models import Pcbang, City


def dashboard_index(request):
    return render(request, "analyzer/index.html")


class CityListView(ListView):
    model = City
    template_name = "analyzer/city-list.html"
    context_object_name = "city_list"
    paginate_by = 10

    def post(self, request, *args, **kwargs):
        if "create_city" in request.POST:
            create_form = CityCreateForm(request.POST)
            if create_form.is_valid():
                create_form.save()

        if "update_city" in request.POST:
            update_form = CityUpdateForm(request.POST)
            if update_form.is_valid():
                city = City.objects.get(pk=request.POST.get("city_id"))
                city.name = update_form.cleaned_data["name"]
                city.save()

        elif "delete_city" in request.POST:
            delete_form = CityDeleteForm(request.POST)
            if delete_form.is_valid():
                city = City.objects.get(pk=delete_form.cleaned_data["city_id"])
                city.delete()

        return self.get(request, *args, **kwargs)

    def get_context_data(self, *, object_list=None, **kwargs):
        context = super().get_context_data(**kwargs)
        return context


class PcbangListView(ListView):
    model = Pcbang
    template_name = "analyzer/pcbang-list.html"
    context_object_name = "pcbang_list"
    paginate_by = 10

    def get_queryset(self):
        return Pcbang.objects.all().order_by("name")

    def get_context_data(self, *, object_list=None, **kwargs):
        context = super().get_context_data(**kwargs)
        return context
