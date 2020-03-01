FROM grubykarol/locust:0.13.5-python3.8-alpine3.11

# Install OpenJDK-8
RUN apk update
RUN apk add --no-cache openjdk8

# Setup JAVA_HOME -- useful for docker commandline

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

# Install Maven
RUN apk add maven

# Needed for the report
RUN apk --update add fontconfig ttf-dejavu

# Copy the files from the machine
RUN mkdir locustapitest
COPY / /locustapitest
WORKDIR /locustapitest

# Mvn Install the project
RUN mvn install

# Publish the Report // swap this part with your own Amazon S3 server or private hosting!
RUN apk add zip unzip

RUN mkdir /locust_report/
RUN ls -la
RUN zip -r /locust_report/report.zip ./target/cucumber-reports/
