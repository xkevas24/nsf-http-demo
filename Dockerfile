FROM openjdk:11-jdk
COPY ddeemmoo-0.0.1-SNAPSHOT.jar /ddeemmoo.jar

CMD java $JAVA_OPTS -jar /ddeemmoo.jar