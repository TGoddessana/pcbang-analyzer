from django.contrib.auth.views import LogoutView
from django.urls import path
from analyzer import views

urlpatterns = [
    path(
        "login/",
        views.LoginView.as_view(),
        name="login",
    ),
    path("logout/", LogoutView.as_view(next_page="login"), name="logout"),
    path("city-list", views.CityListView.as_view(), name="city-list"),
    path("", views.dashboard_index, name="dashboard_index"),
    path("city-create/", views.CityCreateView.as_view(), name="city-create"),
    path("city-udpate/<int:pk>/", views.CityUpdateView.as_view(), name="city-update"),
    path("city-delete/<int:pk>/", views.CityDeleteView.as_view(), name="city-delete"),
    path("pcbang/create/", views.PcbangCreateView.as_view(), name="pcbang-create"),
    path("pcbang-list", views.PcbangListView.as_view(), name="pcbang-list"),
    path(
        "pcbang-update/<int:pk>/",
        views.PcbangUpdateView.as_view(),
        name="pcbang-update",
    ),
    path(
        "pcbang-delete/<int:pk>/",
        views.PcbangDeleteView.as_view(),
        name="pcbang-delete",
    ),
    path(
        "analyze-history-list",
        views.AnalyzeHistoryListView.as_view(),
        name="analyze-history-list",
    ),
    path(
        "analyze-history-list/<int:year>/<int:month>/",
        views.AnalyzeHistoryMonthArchiveView.as_view(month_format="%m"),
        name="analyze-history-monthly",
    ),
    path(
        "analyze-history-list/<int:year>/<int:month>/<int:day>/",
        views.AnalyzeHistoryDayArchiveView.as_view(month_format="%m"),
        name="analyze-history-daily",
    ),
    path(
        "analyze_pcbang/<int:pcbang_id>/",
        views.analyze_pcbang,
        name="analyze-pcbang",
    ),
]
