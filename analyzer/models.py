import socket
from contextlib import closing

from django.db import models


class Pcbang(models.Model):
    name = models.CharField(max_length=100, unique=True)
    address = models.CharField(max_length=100)
    ip = models.GenericIPAddressField()
    port = models.IntegerField(default=5040)
    seat_count = models.IntegerField()
    pc_spec = models.CharField(max_length=100)
    telecom = models.CharField(max_length=100)
    memo = models.TextField()

    @property
    def start_ip(self):
        return self.ip

    @property
    def end_ip(self):
        ip = self.ip.split(".")
        return f"{ip[0]}.{ip[1]}.{ip[2]}.{self.seat_count}"

    def analyze_ip_accessible(self):
        for ip in self._get_ip_range():
            with closing(socket.socket(socket.AF_INET, socket.SOCK_STREAM)) as sock:
                sock.settimeout(1)
                if sock.connect_ex((ip, self.port)) == 0:
                    print(f"Computer at {ip} is ON.")
                else:
                    print(f"Computer at {ip} is OFF or not reachable.")

    def _get_ip_range(self):
        return [
            f'{self.ip.split(".")[0]}.{self.ip.split(".")[1]}.{self.ip.split(".")[2]}.{i}'
            for i in range(1, self.seat_count + 1)
        ]

    def __str__(self):
        return self.name
