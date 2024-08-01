import socket

from django.db import models
from django.db.models.functions import (
    TruncMinute,
)


class City(models.Model):
    name = models.CharField("도시 이름", max_length=100, unique=True)

    class Meta:
        ordering = ["name"]

    def __str__(self):
        return self.name


class Pcbang(models.Model):
    name = models.CharField("매장명", max_length=100, unique=True)
    address = models.CharField("주소", max_length=100)
    ip = models.GenericIPAddressField("IP")
    port = models.IntegerField("포트", default=5040)
    seat_count = models.IntegerField("좌석수")
    pc_spec = models.CharField("PC 사양", max_length=100)
    telecom = models.CharField("통신사", max_length=100)
    memo = models.TextField("메모", blank=True)
    city = models.ForeignKey(City, on_delete=models.CASCADE)

    def get_last_analysis(self):
        return self.analyzehistory_set.first()

    @property
    def start_ip(self):
        return self.ip

    @property
    def end_ip(self):
        ip = self.ip.split(".")
        return f"{ip[0]}.{ip[1]}.{ip[2]}.{int(ip[3]) + self.seat_count}"

    def analyze_ip_accessible(self, analyzed_at, insert=False):
        open_count = 0
        close_count = 0

        for ip in self._get_ip_range():
            try:
                connection = socket.create_connection((ip, self.port), timeout=0.05)
                open_count += 1
                connection.close()
            except socket.error:
                close_count += 1

        if insert:
            AnalyzeHistory.objects.create(
                open_count=open_count,
                close_count=close_count,
                pcbang=self,
                analyzed_at=analyzed_at,
            )

        return open_count, close_count

    def _get_ip_range(self):
        start_ip = self.start_ip.split(".")
        end_ip = self.end_ip.split(".")
        return [
            f"{start_ip[0]}.{start_ip[1]}.{start_ip[2]}.{i}"
            for i in range(int(start_ip[3]), int(end_ip[3]) + 1)
        ]

    def __str__(self):
        return self.name


class AnalyzeHistory(models.Model):
    class _Manager(models.Manager):

        def get_queryset(self):
            return (
                super()
                .get_queryset()
                .annotate(
                    minute=TruncMinute("analyzed_at"),
                )
                .select_related("pcbang")
                .distinct()
                .order_by("-minute")
            )

    open_count = models.IntegerField("켜져 있는 좌석 수")
    close_count = models.IntegerField("꺼져 있는 좌석 수")
    analyzed_at = models.DateTimeField("분석일시")
    pcbang = models.ForeignKey(Pcbang, on_delete=models.CASCADE)

    objects = _Manager()

    class Meta:
        ordering = ["-analyzed_at"]

    @property
    def open_rate(self):
        return self.open_count / (self.open_count + self.close_count) * 100

    def __str__(self):
        return f"{self.pcbang.name} - {self.analyzed_at}"
