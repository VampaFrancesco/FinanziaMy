# Usa un'immagine base di Ubuntu
FROM ubuntu:20.04

# Installa OpenJDK 23 e altri strumenti necessari
RUN apt-get update && apt-get install -y \
    software-properties-common \
    && add-apt-repository ppa:openjdk-r/ppa \
    && apt-get update && apt-get install -y \
    openjdk-23-jdk \
    maven \
    xvfb \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    && apt-get clean

# Configura Java e Maven
ENV JAVA_HOME=/usr/lib/jvm/java-23-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH
ENV DISPLAY=:99
ENV MAVEN_OPTS="-Djava.awt.headless=true -Xmx2048m"

# Crea una directory di lavoro per l'app
WORKDIR /app

# Copia tutto il progetto nel container
COPY . .

# Debug del display
RUN echo "Verifica configurazione Xvfb: DISPLAY=$DISPLAY"

# Avvia Xvfb e i test Maven
CMD Xvfb :99 -screen 0 1920x1080x24 > /dev/null 2>&1 && mvn clean verify
