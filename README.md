# RESTful-сервис для учёта носков

Простое приложение, которое предоставляет REST API для управления учётом носков в базе данных. 

# Использованные технологии

* IntelliJ IDEA
* Java 17
* Gradle
* OpenCSV
* Spring (Spring Boot, Spring Core, Spring MVC, Spring Data JPA)
* PostgreSQL 17
* JPA/Hibernate 
* JUnit 5
* Mockito
* OpenAPI (Springdoc)

# Установка и запуск

1. Скачать и распаковать архив SockInventorySystem из [релизов](https://github.com/qwert312/Sock-Inventory-System/releases/latest).
2. Поменять под свою базу данных application.properties.
3. Запустить НАЗВАНИЕ.jar

Перед первым запуском нужно убрать # в строке #spring.jpa.hibernate.ddl-auto=create в application.properties. После
первого запуска необходимо её вернуть, чтобы структура базы данных не создавалась каждый раз заново.

Дальше доступ к конечным точкам можно получить через стандартный localhost:8080. Например localhost:8080/api/socks для
получения количества носков по заданным критериям.

# Документация

OpenAPI документация, описывающая RESTful-сервис, есть в виде json файла openapi.json как в [релизах](https://github.com/qwert312/Sock-Inventory-System/releases/latest),
так и доступна для скачивания прямо из репозитория, в папке spec. Скачанный файл можно использовать
вместе со Swagger UI для получения более удобного вида документации.