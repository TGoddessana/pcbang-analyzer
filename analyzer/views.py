from django.shortcuts import render
from django.views.generic import ListView

from analyzer.forms import CityForm
from analyzer.models import Pcbang, City


def dashboard_index(request):
    return render(request, "analyzer/index.html")


class CityListView(ListView):
    model = City
    template_name = "analyzer/city-list.html"
    context_object_name = "city_list"
    paginate_by = 10

    def post(self, request, *args, **kwargs):
        form = CityForm(self.request.POST)
        if form.is_valid():
            form.save()
        return super().get(self.request)

    def get_context_data(self, *, object_list=None, **kwargs):
        context = super().get_context_data(**kwargs)
        context["form"] = CityForm()
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
