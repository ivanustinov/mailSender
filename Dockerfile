# Используем официальный образ OpenJDK 16
FROM adoptopenjdk:16-jdk-hotspot

ENV CONF_DIR=/mailsender/

# Устанавливаем рабочий каталог
WORKDIR /mailsender

# Помещаем JAR-файл в контейнер
COPY target/mailSender-1.0.war .

COPY config/mail.properties ./config

# Копируем сертификат в контейнер
COPY jssecacerts .

# Импортируем сертификат в Truststore
RUN keytool -exportcert -alias smtp.yandex.ru-1 -keystore jssecacerts -storepass changeit -file smtp.yandex.ru.cer
RUN yes | keytool -importcert -alias smtp.yandex.ru -cacerts -storepass changeit -file smtp.yandex.ru.cer
# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "mailSender-1.0.war"]