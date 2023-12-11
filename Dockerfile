FROM openjdk:11-jdk
EXPOSE 8080
COPY target/ddeemmoo-0.0.1-SNAPSHOT.jar /ddeemmoo-0.0.1-SNAPSHOT.jar
CMD java $JAVA_OPTS -jar /ddeemmoo-0.0.1-SNAPSHOT.jar $DEMO_VERSION $DEMO_COLOR
# docker build -t harbor.cloud.netease.com/qztest/nsf-http-demo:v4.1.2 .
# docker push harbor.cloud.netease.com/qztest/nsf-http-demo:v4.1.2