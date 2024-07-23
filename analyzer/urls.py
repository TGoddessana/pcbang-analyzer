from django.urls import path
from analyzer import views

urlpatterns = [
    path("city-list", views.CityListView.as_view(), name="city-list"),
    path("", views.dashboard_index, name="dashboard_index"),
    path("city-create/", views.CityCreateView.as_view(), name="city-create"),
    path("city-udpate/<int:pk>/", views.CityUpdateView.as_view(), name="city-update"),
    path("city-delete/<int:pk>/", views.CityDeleteView.as_view(), name="city-delete"),
    path("pcbang/create/", views.PcbangCreateView.as_view(), name="pcbang-create"),
    path("pcbang-list", views.PcbangListView.as_view(), name="pcbang-list"),
    path(
        "pcbang/update/<int:pk>/",
        views.PcbangUpdateView.as_view(),
        name="pcbang-update",
    ),
    path(
        "pcbang/delete/<int:pk>/",
        views.PcbangDeleteView.as_view(),
        name="pcbang-delete",
    ),
]
