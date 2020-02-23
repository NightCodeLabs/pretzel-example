FROM grubykarol/locust:0.10.0-python3.6-alpine3.9

USER root

# Install OpenJDK-8
RUN apk update
RUN apk add --no-cache openjdk8

# Setup JAVA_HOME -- useful for docker commandline

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

# Install Maven
RUN apk add maven

# Copy the files from the machine

COPY / /locust
RUN cd /locust

# Mvn Install the project
RUN mvn install