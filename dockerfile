FROM eclipse-temurin:22-jre-jammy
# copy application JAR (with libraries inside)

COPY target/application-*.jar /opt/application.jar
# specify default command
CMD ["/opt/java/openjdk/bin/java", "-jar", "/opt/application.jar"]
