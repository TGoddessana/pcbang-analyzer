from django.shortcuts import render
from django.views.generic import ListView

from .models import Pcbang


def dashboard_index(request):
    return render(request, "analyzer/index.html")


class PcbangListView(ListView):
    model = Pcbang
    template_name = "analyzer/pcbang-list.html"
    context_object_name = "pcbang_list"
    paginate_by = 10

    def get_queryset(self):
        return Pcbang.objects.all().order_by("name")
