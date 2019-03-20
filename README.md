# Education. Backend with Spring Boot

Демо проект для сохранения накопленных знаний.

Проект в постоянном статусе доработки, т.к. технологии меняются и развитию нет предела.

### Модули:
##### 1. education-rest
Слой для RESTful Веб Сервисов. Авторизация через [HTTP Basic Auth]. Защита [CSRF].   
Юнит тестирование: Spring MockMVC
##### 2. education-rest-api
Библиотека для DTO классов.  
Юнит тестирование: Не требуется
##### 3. education-service
Слой для бизнес логики. На этом уровне управляем транзакцией. Доступ к методам через роли. Конвертация Entity-DTO осуществляется библиотекой [Orika Mapper].  
Юнит тестирование: [Mockito], [AssertJ]
##### 4. education-repo
Слой для работы с репозиторием данных (база данных [H2] или [PostgreSQL]).  
Юнит тестирование: [AssertJ]

## Сборка
##### Maven
```bash
$ mvn clean package
```
Плагин [PMD] принудительно выполняет статический анализ качества кода. 

## Отчет о прохождении юнит тестов
```bash
$ mvn allure:report
```
В каждом модуле создаётся свой Allure отчёт в папке target/allure-report.

## Запуск
```bash
$ java -jar education-rest-1.0.0-SNAPSHOT.war
```
Есть возможность задать внешний каталог с файлами настроек.
```bash
$ java -Dcatalina.base=d:\\education -jar education-rest-1.0.0-SNAPSHOT.war
```
По-умолчанию запускается база данных [H2] в памяти. Через настройки из внешнего файла можно указать базу данных [PostgreSQL].

Spring Boot запустит приложение по адресу http://localhost:8080/education/rest
```
GET http://localhost:8080/education/rest/users/auth
#root:changeit
Authorization: Basic cm9vdDpjaGFuZ2VpdA==
Content-Type: application/json;charset=UTF-8
```
В ответ будет получен JSON с информацией об авторизованном пользователе.
```
GET http://localhost:8080/education/rest/users
#ivanov:changeit
Authorization: Basic aXZhbm92OmNoYW5nZWl0
Content-Type: application/json;charset=UTF-8
```
В ответ будет получена ошибка со статусом 403, т.к. у пользователя нет необходимой роли доступа.
```
GET http://localhost:8080/education/rest/users
Authorization: Basic cm9vdDpjaGFuZ2VpdA==
Content-Type: application/json;charset=UTF-8
```
В ответ будет получен JSON со списком пользователей.
```
GET http://localhost:8080/education/rest/users/1
#ivanov:changeit
Authorization: Basic aXZhbm92OmNoYW5nZWl0
Content-Type: application/json;charset=UTF-8
```
В ответ будет получен JSON с информацией о пользователе с id = 1.

## Использованы библиотеки

  - [Java SE](https://www.oracle.com/technetwork/java/javase/overview) 8 (также работает на SE 11)
  - [Spring Framework 5](http://spring.io) (Boot, Data JPA, Security, Actuator)
  - [Liquibase](https://www.liquibase.org) + [H2] + [PostgreSQL]
  - [Logback](https://logback.qos.ch)
  - [Lombok](https://projectlombok.org)
  - [Orika Mapper](https://github.com/orika-mapper/orika)
  - [Guava](https://github.com/google/guava)
  - [Vavr](http://www.vavr.io)
  - [Mockito](https://site.mockito.org)
  - [AssertJ](http://joel-costigliola.github.io/assertj)
  - [Yandex Allure 2](https://github.com/allure-framework/allure2)
  - [JUnit](https://junit.org)

[H2]:http://www.h2database.com
[PostgreSQL]:https://www.postgresql.org
[Orika Mapper]:https://github.com/orika-mapper/orika
[Mockito]:https://site.mockito.org
[AssertJ]:http://joel-costigliola.github.io/assertj
[HTTP Basic Auth]:https://en.wikipedia.org/wiki/Basic_access_authentication
[CSRF]:https://ru.wikipedia.org/wiki/Межсайтовая_подделка_запроса
[PMD]:https://maven.apache.org/plugins/maven-pmd-plugin