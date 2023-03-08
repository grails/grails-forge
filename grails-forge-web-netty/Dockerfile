#FROM ghcr.io/graalvm/graalvm-ce:java8-21.0.0.2 as graalvm
# For JDK 11
FROM ghcr.io/graalvm/graalvm-ce:java11-21.0.0.2 as graalvm
RUN gu install native-image

COPY . /home/app/grails-forge
WORKDIR /home/app/grails-forge

RUN native-image -cp build/libs/grails-forge-web-netty-*-all.jar

FROM adoptopenjdk/openjdk11:alpine-slim
EXPOSE 8080
COPY --from=graalvm /home/app/grails-forge/grails-forge /app/grails-forge
ENTRYPOINT ["/app/grails-forge"]
