from django.views.generic import ListView

from .models import Pcbang


class PcbangListView(ListView):
    model = Pcbang
    template_name = "pcbang_list.html"
    context_object_name = "pcbang_list"
    paginate_by = 10

    def get_queryset(self):
        return Pcbang.objects.all().order_by("name")
