FROM ubuntu:20.04

RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    xvfb \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    && apt-get clean

ENV DISPLAY=:99
RUN Xvfb :99 -screen 0 1920x1080x24 > /dev/null 2>&1 &
