
```bash
docker build  --build-arg JENKINS_REMOTING_TAG=4.13.2-1-jdk11 --tag techierishi/jenkins-agent-core:4.13.2-1-jdk11 .
docker push techierishi/jenkins-agent-core:jenkins-agent-core:4.13.2-1-jdk11 .
```


```Dockerfile
ARG JENKINS_REMOTING_TAG

FROM jenkins/inbound-agent:$JENKINS_REMOTING_TAG
ENV JENKINS_HOME=/home/jenkins

COPY build/install-esh.sh /tmp/build/install-esh.sh

WORKDIR ${JENKINS_HOME}

USER root

RUN set -ex && \
    apt-get update && \
    apt-get install -y \
        apt-transport-https \
        bash \
        bc \
        software-properties-common \
        ca-certificates \
        curl \
        expect \
        git \
        gpg \
        jq \
        make \
        python3 \
        python3-pip \
        python3-venv \
        unixodbc-dev \
        dos2unix \
        shellcheck \
        gnupg \
        zip \
        && \
    pip3 install --upgrade \
        virtualenv \
        PyYAML==3.12 \
        XlsxWriter==1.2.7 \
        Jinja2==2.9.6 \
        MarkupSafe==1.1.1 \
        && \
    ln -s /usr/bin/python3 /usr/bin/python && \
    /tmp/build/install-esh.sh v0.3.1 && \
    rm -rf /tmp/build && \
    mkdir -p /usr/share/man/man1/ && \
    touch /usr/share/man/man1/sh.distrib.1.gz

# Install JAVA 8
RUN curl -fsSL https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | gpg --dearmor -o /usr/share/keyrings/adoptopenjdk-archive-keyring.gpg
RUN echo "deb [arch=arm64 signed-by=/usr/share/keyrings/adoptopenjdk-archive-keyring.gpg] https://adoptopenjdk.jfrog.io/adoptopenjdk/deb bullseye main" > /etc/apt/sources.list.d/adoptopenjdk.list
RUN apt-get update
RUN apt-get install -y adoptopenjdk-8-hotspot
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# ENV JAVA_HOME /usr/lib/jvm/adoptopenjdk-8-hotspot-arm64

# Install maven
RUN curl -L -o /tmp/apache-maven.zip https://downloads.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip
RUN mkdir -p /opt/maven
RUN unzip /tmp/apache-maven.zip -d /opt/maven
RUN rm /tmp/apache-maven.zip
ENV MAVEN_HOME /opt/maven/apache-maven-3.9.5
ENV PATH $MAVEN_HOME/bin:$PATH

# change /bin/sh to use bash, because lots of our scripts use bash features
RUN echo "dash dash/sh boolean false" | debconf-set-selections && \
    DEBIAN_FRONTEND=noninteractive dpkg-reconfigure dash

USER jenkins

ENTRYPOINT ["jenkins-agent"]

```
