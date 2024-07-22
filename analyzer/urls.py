from django.urls import path
from analyzer import views

urlpatterns = [
    path("", views.dashboard_index, name="dashboard_index"),
    path("pcbang-list", views.PcbangListView.as_view(), name="pcbang_list"),
]
