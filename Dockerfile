FROM python:3.10-slim

WORKDIR /app

COPY pyproject.toml poetry.lock /app/

RUN pip install poetry

RUN poetry install --no-root

COPY . /app

COPY entrypoint.sh /entrypoint.sh

RUN chmod u+x /entrypoint.sh