## О проекте.
* Данный проект - Spring boot приложение, представляет собой реализацию чата c комнатами.
* В качестве БД используется PostgreSQL.
* Работа с БД осуществляется через Spring Data JPA.
* Слой контроллеров использует Spring MVC.
* Все контроллеры построены по REST-архитектуре.
* Для проверки приложения используется Postman.

## Сборка

Для сборки проекта необходимо:
1. Установить JDK 11.
2. Установить Maven.
3. Установить Postman.
4. Установить сервер БД PostgreSQL, задать логин - *postgres*, пароль - *password*.
5. Скачать исходный код проекта.
6. Создать в pg_Admin БД *chat*.
7. Открыть Query Tool для созданной БД и запустить SQL-скрипт `update_001.sql` из папки `db`.
8. Перейти в корень проекта, где лежит файл `pom.xml`.
9. Собрать проект командой `mvn -DskipTests=true package`.
   При успешной сборке должна появиться папка target c `chat-1.jar`.

#### Описание

#### Технологии
> JDK11, Maven, Spring Boot, REST


## Использование

### Контакты:
[<img align="left" alt="telegram" width="18px" src="https://cdn.jsdelivr.net/npm/simple-icons@3.3.0/icons/telegram.svg" />][telegram]
[<img align="left" alt="gmail" width="18px" src="https://cdn.jsdelivr.net/npm/simple-icons@3.3.0/icons/gmail.svg" />][gmail]
[<img align="left" alt="LinkedIn" width="18px" src="https://cdn.jsdelivr.net/npm/simple-icons@v3/icons/linkedin.svg" />][linkedin]


[telegram]: https://t.me/GrokDen
[gmail]: mailto:den.voiten@gmail.com
[linkedin]: https://www.linkedin.com/in/denis-voytenko-585488117/

