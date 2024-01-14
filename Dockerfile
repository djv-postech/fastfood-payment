FROM amazoncorretto:17-alpine-jdk
ADD target/fastfood-payment-0.0.1.jar fastfood-payment.jar
ENTRYPOINT ["java", "-jar", "fastfood-payment.jar"]
EXPOSE 8083