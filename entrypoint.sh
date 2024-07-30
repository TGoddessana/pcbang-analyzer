#!/bin/bash

if [ "$1" = "bash" ]; then
  exec "$@"
else
  poetry run python manage.py migrate
  poetry run python manage.py collectstatic --noinput
  poetry run celery -A config worker --loglevel=info &
  poetry run celery -A config beat --loglevel=info &
  exec poetry run python manage.py runserver 0.0.0.0:8000
fi