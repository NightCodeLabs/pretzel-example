FROM grubykarol/locust:0.13.5-python3.8-alpine3.11

USER root

# Install OpenJDK-8
RUN apk update
RUN apk add --no-cache openjdk8

# Setup JAVA_HOME -- useful for docker commandline

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

# Install Maven
RUN apk add maven

#Install locustio

RUN pip install locustio

# Copy the files from the machine

COPY / /locust
RUN cd /locust

# Mvn Install the project
RUN mvn install