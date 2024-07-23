from django.utils import timezone

from celery import shared_task, group
from .models import Pcbang


@shared_task
def analyze_single_pcbang(pcbang_id, analyzed_at):
    pcbang = Pcbang.objects.get(id=pcbang_id)
    pcbang.analyze_ip_accessible(analyzed_at=analyzed_at, insert=True)


@shared_task
def analyze_pcbangs():
    start_time = timezone.now()
    pcbang_ids = Pcbang.objects.values_list("id", flat=True)
    job = group(
        analyze_single_pcbang.s(pcbang_id, start_time) for pcbang_id in pcbang_ids
    )
    job.apply_async()
