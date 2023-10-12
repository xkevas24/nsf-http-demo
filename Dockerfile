FROM openjdk:11-jdk
COPY target/ddeemmoo-0.0.1-SNAPSHOT.jar /ddeemmoo.jar
CMD java $JAVA_OPTS -jar /ddeemmoo.jar

# docker build -t harbor.cloud.netease.com/qztest/nsf-http-demo:v2.4.0 .
# docker push harbor.cloud.netease.com/qztest/nsf-http-demo:v2.4.0
# v2的版本号是v2
# docker build -t harbor.cloud.netease.com/qztest/nsf-http-demo:v2 .
# docker push harbor.cloud.netease.com/qztest/nsf-http-demo:v2

