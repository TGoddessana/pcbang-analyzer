from django.urls import path
from analyzer.views import PcbangListView

urlpatterns = [
    path("pcbang-list", PcbangListView.as_view(), name="pcbang_list"),
]
