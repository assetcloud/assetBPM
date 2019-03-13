# Package
FROM anapsix/alpine-java:8_server-jre_unlimited

ADD target/abpm-process-center*.jar app.jar

ENTRYPOINT ["java", "-jar", "/usr/share/myservice/myservice.jar"]

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/myservice/myservice.jar

EXPOSE 9000


#docker push 192.168.1.202/lujw/abpm:1.0-SNAPSHOT
#docker run -d  -p 9000:9000  192.168.1.202/lujw/abpm:1.0-SNAPSHOT

