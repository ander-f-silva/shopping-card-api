FROM ghcr.io/graalvm/graalvm-ce:latest

ENV APP_HOME "/opt/app"

ADD ./build/libs/shopping-card-api-0.1-all.jar $APP_HOME/shopping-card-api-0.1-all.jar

CMD	java -Xmx512M -Xms256M -jar /opt/app/shopping-card-api-0.1-all.jar