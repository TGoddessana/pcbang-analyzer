from datetime import datetime

from django.contrib.auth.decorators import login_required
from django.db.models.functions import TruncMinute

from django.http import JsonResponse
from django.shortcuts import render
from django.utils.decorators import method_decorator
from django.views.generic import ListView
from django.urls import reverse_lazy
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from analyzer.forms import (
    CityCreateForm,
    CityUpdateForm,
    PcbangCreateForm,
    PcbangUpdateForm,
)
from analyzer.models import Pcbang, City, AnalyzeHistory
from django.contrib.auth.views import LoginView as DjangoLoginView


class LoginView(DjangoLoginView):
    template_name = "auth/_login.html"


@login_required
def dashboard_index(request):
    pcbang_list = Pcbang.objects.all()
    pcbang_count = Pcbang.objects.count()

    recent_history = AnalyzeHistory.objects.first()
    if recent_history:
        recent_histories = AnalyzeHistory.objects.filter(
            analyzed_at__year=recent_history.minute.year,
            analyzed_at__month=recent_history.minute.month,
            analyzed_at__day=recent_history.minute.day,
            analyzed_at__hour=recent_history.minute.hour,
            analyzed_at__minute=recent_history.minute.minute,
        )
        highest_open_rate_history = max(recent_histories, key=lambda h: h.open_rate)
    else:
        recent_histories = None
        highest_open_rate_history = None

    return render(
        request,
        "analyzer/index.html",
        {
            "pcbang_list": pcbang_list,
            "pcbang_count": pcbang_count,
            "highest_open_rate_history": highest_open_rate_history,
            "recent_history_analyzed_at": (
                recent_history.analyzed_at if recent_history else None
            ),
            "recent_histories": recent_histories,
        },
    )


@method_decorator(login_required, name="dispatch")
class CityCreateView(CreateView):
    model = City
    form_class = CityCreateForm
    template_name = "analyzer/city-form.html"
    success_url = reverse_lazy("city-list")


@method_decorator(login_required, name="dispatch")
class CityListView(ListView):
    model = City
    template_name = "analyzer/city-list.html"
    context_object_name = "city_list"


@method_decorator(login_required, name="dispatch")
class CityUpdateView(UpdateView):
    model = City
    form_class = CityUpdateForm
    template_name = "analyzer/city-form.html"
    success_url = reverse_lazy("city-list")


@method_decorator(login_required, name="dispatch")
class CityDeleteView(DeleteView):
    model = City
    template_name = "analyzer/city-delete.html"
    success_url = reverse_lazy("city-list")


@method_decorator(login_required, name="dispatch")
class PcbangCreateView(CreateView):
    model = Pcbang
    form_class = PcbangCreateForm
    template_name = "analyzer/pcbang-form.html"
    success_url = reverse_lazy("pcbang-list")

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context["city_list"] = City.objects.all()
        return context


@method_decorator(login_required, name="dispatch")
class PcbangListView(ListView):
    model = Pcbang
    template_name = "analyzer/pcbang-list.html"
    context_object_name = "pcbang_list"

    def get_queryset(self):
        return Pcbang.objects.all().order_by("name")

    def get_context_data(self, *, object_list=None, **kwargs):
        context = super().get_context_data(**kwargs)
        return context


@method_decorator(login_required, name="dispatch")
class PcbangUpdateView(UpdateView):
    model = Pcbang
    form_class = PcbangUpdateForm
    template_name = "analyzer/pcbang-form.html"
    success_url = reverse_lazy("pcbang-list")

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context["city_list"] = City.objects.all()
        return context


@method_decorator(login_required, name="dispatch")
class PcbangDeleteView(DeleteView):
    model = Pcbang
    template_name = "analyzer/pcbang-delete.html"
    success_url = reverse_lazy("pcbang-list")


@method_decorator(login_required, name="dispatch")
class AnalyzeHistoryListView(ListView):
    model = AnalyzeHistory
    template_name = "analyzer/analyze-history-list.html"
    context_object_name = "grouped_histories"
    paginate_by = 10

    def get_queryset(self):
        city_name = self.request.GET.get("city_name", "")
        pcbang_name = self.request.GET.get("pcbang_name", "")
        queryset = (
            AnalyzeHistory.objects.filter(
                pcbang__city__name__icontains=city_name,
                pcbang__name__icontains=pcbang_name,
            )
            .annotate(minute=TruncMinute("analyzed_at"))
            .distinct()
            .order_by("-minute")
        )
        return queryset

    def get_city_list(self):
        return City.objects.values_list("name", flat=True).distinct()

    def get_pcbang_list(self):
        return Pcbang.objects.values_list("name", flat=True).distinct()

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        page_obj = context["page_obj"]

        grouped_histories = {}
        for item in page_obj:
            dt = item.minute
            histories = AnalyzeHistory.objects.filter(
                analyzed_at__year=dt.year,
                analyzed_at__month=dt.month,
                analyzed_at__day=dt.day,
                analyzed_at__hour=dt.hour,
                analyzed_at__minute=dt.minute,
                pcbang__city__name__icontains=self.request.GET.get("city_name", ""),
                pcbang__name__icontains=self.request.GET.get("pcbang_name", ""),
            )
            grouped_histories[dt] = histories

        context["grouped_histories"] = grouped_histories
        context["city_list"] = self.get_city_list()
        context["pcbang_list"] = self.get_pcbang_list()
        context["city_name"] = self.request.GET.get("city_name", "")
        context["pcbang_name"] = self.request.GET.get("pcbang_name", "")
        return context


@login_required
def analyze_pcbang(request, pcbang_id):
    pcbang = Pcbang.objects.get(id=pcbang_id)
    open_count, close_count = pcbang.analyze_ip_accessible(datetime.now(), insert=False)
    return JsonResponse({"open_count": open_count, "close_count": close_count})
