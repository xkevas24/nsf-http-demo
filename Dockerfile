FROM openjdk:11-jdk
COPY target/ddeemmoo-0.0.1-SNAPSHOT.jar /ddeemmoo-0.0.1-SNAPSHOT.jar
CMD java $JAVA_OPTS -jar /ddeemmoo-0.0.1-SNAPSHOT.jar
# docker build -t harbor.cloud.netease.com/qztest/product-info:poc .
# docker push harbor.cloud.netease.com/qztest/product-info:poc

# docker build -t harbor.cloud.netease.com/qztest/java-product-api:poc .
# docker push harbor.cloud.netease.com/qztest/java-product-api:poc