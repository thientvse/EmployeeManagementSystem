FROM openjdk:8
EXPOSE 8087
ADD target/employee-management.jar employee-management.jar
ENTRYPOINT ["java","-jar","/employee-management.jar"]