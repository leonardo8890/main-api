FROM ubuntu:latest
LABEL authors="leonardo"

ENTRYPOINT ["top", "-b"]