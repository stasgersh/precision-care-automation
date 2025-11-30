FROM us-artifactory.apps.ge-healthcare.net/docker-precisioninsights-dev-all/debian:12.8 AS base

ARG CHROME_VERSION=133.0.6943.53
ARG MAVEN_VERSION=3.6.3
ENV TZ Europe/Budapest

# Install base dependencies
RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    gdebi-core \
    jq \
    git

# Install certificates
ARG GEHC_CERTS_PATH=/usr/local/share/ca-certificates
RUN for filename in ${GEHC_CERTS_PATH}/*.crt; do cat >> /etc/ssl/certs/ca-certificates.crt ${filename} ; done
RUN update-ca-certificates

# Install JDK 21 Corretto
RUN wget -O - https://apt.corretto.aws/corretto.key | gpg --dearmor -o /usr/share/keyrings/corretto-keyring.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/corretto-keyring.gpg] https://apt.corretto.aws stable main" | tee /etc/apt/sources.list.d/corretto.list && \
    apt-get update && apt-get install -y java-21-amazon-corretto-jdk
    
ENV JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto
# Install Maven
RUN wget -q -O /tmp/apache-maven.tar.gz https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/${MAVEN_VERSION}/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar xf /tmp/apache-maven.tar.gz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/local/bin/mvn 

# Install certificates for java
RUN for filename in ${GEHC_CERTS_PATH}/*.crt; do keytool -import -trustcacerts -file ${filename} -keystore $JAVA_HOME/lib/security/cacerts -alias ${filename} -storepass changeit -noprompt; done

RUN wget -q --no-check-certificate http://archive.ubuntu.com/ubuntu/pool/main/libu/libu2f-host/libu2f-udev_1.1.4-1_all.deb && \
    dpkg -i libu2f-udev_1.1.4-1_all.deb && \
    wget -q --no-check-certificate https://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_${CHROME_VERSION}-1_amd64.deb && \
    gdebi --non-interactive google-chrome*.deb && \
    rm -f google-chrome*.deb && \
    apt-get -y clean

RUN useradd -ms /bin/bash user
WORKDIR /opt/onchron-e2e-tests
RUN chmod -R 777 /opt/onchron-e2e-tests
USER user

COPY --chmod=777 pom.xml .

RUN mkdir -p /home/user/.m2/
COPY --chmod=777 settings.xml /home/user/.m2/
# saves time when rebuilding image multiple times by caching depenency step before copy
RUN mvn -B dependency:go-offline dependency:resolve-plugins

COPY --chmod=777 . .

RUN git config --global --add safe.directory /opt/onchron-e2e-tests

RUN echo "\ndrivers.linux.webdriver.chrome.driver=root/.cache/selenium/chromedriver/linux64/$CHROME_VERSION/chromedriver" >> serenity.properties
RUN echo "chrome.switches=--ignore-ssl-errors=yes;--ignore-certificate-errors;--no-proxy-server;--proxy-server='direct://';--proxy-bypass-list=*;--remote-allow-origins=*;--headless=new;--disable-dev-shm-usage;--no-sandbox" >> serenity.properties
RUN echo "\nlog4j.appender.test.append = true" >> ./src/main/resources/log4j.properties

RUN --mount=type=secret,id=serenity.properties,dst=/opt/onchron-e2e-tests/serenity.properties mvn clean install -DskipTests=true
