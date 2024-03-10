FROM openjdk:17-ea-17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /footballTeamManage.jar
CMD [ "java","-jar","footballTeamManage.jar" ]