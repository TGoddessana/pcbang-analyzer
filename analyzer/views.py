from datetime import datetime
from datetime import datetime
from django.core.paginator import Paginator
from django.db.models.functions import (
    TruncYear,
    TruncMonth,
    TruncDay,
    TruncHour,
    TruncMinute,
)
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
from analyzer.models import Pcbang, City, AnalyzeHistory


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
            "recent_history_analyzed_at": recent_history.analyzed_at,
            "recent_histories": recent_histories,
        },
    )


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


class PcbangCreateView(CreateView):
    model = Pcbang
    form_class = PcbangCreateForm
    template_name = "analyzer/pcbang-form.html"
    success_url = reverse_lazy("pcbang-list")

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context["city_list"] = City.objects.all()
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


def analyze_history_view(request):
    unique_datetimes = AnalyzeHistory.objects.get_queryset().values("minute")

    paginator = Paginator(unique_datetimes, 10)
    page_number = request.GET.get("page")
    page_obj = paginator.get_page(page_number)

    grouped_histories = {}
    for item in page_obj:
        dt = datetime(
            year=item["minute"].year,
            month=item["minute"].month,
            day=item["minute"].day,
            hour=item["minute"].hour,
            minute=item["minute"].minute,
        )
        histories = AnalyzeHistory.objects.filter(
            analyzed_at__year=dt.year,
            analyzed_at__month=dt.month,
            analyzed_at__day=dt.day,
            analyzed_at__hour=dt.hour,
            analyzed_at__minute=dt.minute,
        )
        grouped_histories[dt] = histories

    context = {"page_obj": page_obj, "grouped_histories": grouped_histories}
    return render(request, "analyzer/analyze-history-list.html", context)
