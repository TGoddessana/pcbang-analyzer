from django.shortcuts import render
from django.views.generic import ListView
from django.urls import reverse_lazy
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from analyzer.forms import (
    CityCreateForm,
    CityUpdateForm,
    PcbangCreateForm,
    PcbangUpdateForm,
)
from analyzer.models import Pcbang, City


def dashboard_index(request):
    return render(request, "analyzer/index.html")


class CityCreateView(CreateView):
    model = City
    form_class = CityCreateForm
    template_name = "analyzer/city-form.html"
    success_url = reverse_lazy("city-list")


class CityListView(ListView):
    model = City
    template_name = "analyzer/city-list.html"
    context_object_name = "city_list"
    paginate_by = 10


class CityUpdateView(UpdateView):
    model = City
    form_class = CityUpdateForm
    template_name = "analyzer/city-form.html"
    success_url = reverse_lazy("city-list")


class CityDeleteView(DeleteView):
    model = City
    template_name = "analyzer/city-delete.html"
    success_url = reverse_lazy("city-list")


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


class PcbangCreateView(CreateView):
    model = Pcbang
    form_class = PcbangCreateForm
    template_name = "analyzer/pcbang-form.html"
    success_url = reverse_lazy("pcbang-list")

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context["city_list"] = City.objects.all()
        return context


class PcbangUpdateView(UpdateView):
    model = Pcbang
    form_class = PcbangUpdateForm
    template_name = "analyzer/pcbang-form.html"
    success_url = reverse_lazy("pcbang-list")

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context["city_list"] = City.objects.all()
        return context


class PcbangDeleteView(DeleteView):
    model = Pcbang
    template_name = "analyzer/pcbang-delete.html"
    success_url = reverse_lazy("pcbang-list")
