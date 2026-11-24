# Лабораторная работа №5 — Swagger UI Integration with Spring Boot Web Application

REST API пользователей (`/users`) с документацией OpenAPI и Swagger UI на базе
`springdoc-openapi-starter-webmvc-ui`.

## Стек

- Java 17, Maven
- Spring Boot 3.1.12 (`spring-boot-starter-web`)
- springdoc-openapi-starter-webmvc-ui 2.1.0 (версия из методички; совместима со Spring Boot 3.0/3.1)
- Тесты: JUnit 5, MockMvc, Mockito, AssertJ, Datafaker, JsonUnit

База данных в работе не используется (по методичке — «если это необходимо»).

## Запуск

```bash
mvn spring-boot:run
```

Порт приложения: **8081** (`server.port` в `src/main/resources/application.yml`).

Тесты:

```bash
mvn test
```

## Документация API

| Что | URL |
|-----|-----|
| Swagger UI | http://localhost:8081/swagger-ui.html (перенаправляет на `/swagger-ui/index.html`) |
| OpenAPI (JSON) | http://localhost:8081/v3/api-docs |
| OpenAPI (YAML) | http://localhost:8081/v3/api-docs.yaml |

Настройки springdoc (`application.yml`):

```yaml
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
```

В Swagger UI раскройте `user-controller`, выберите метод, нажмите **Try it out**, заполните
параметры и нажмите **Execute** — ответ появится в секции **Responses**.

## Демонстрация (curl)

1. Swagger UI — редирект:

   ```bash
   curl -i http://localhost:8081/swagger-ui.html
   # HTTP/1.1 302, Location: /swagger-ui/index.html
   ```

2. Описание OpenAPI в JSON и YAML:

   ```bash
   curl http://localhost:8081/v3/api-docs
   curl http://localhost:8081/v3/api-docs.yaml
   ```

3. Создание пользователя:

   ```bash
   curl -i -X POST http://localhost:8081/users/create \
        -H 'Content-Type: application/json' \
        -d '{"name":"Ivan","email":"ivan@example.com"}'
   # 200, «Пользователь создан»
   ```

4. Получение пользователя по id:

   ```bash
   curl -i http://localhost:8081/users/42
   # 200, {"id":"42","email":"example@example.com"}
   ```

## Эндпоинты

| Метод | Путь | Тело запроса | Ответ |
|-------|------|--------------|-------|
| POST | `/users/create` | `UserRequest {name, email}` | `200`, строка «Пользователь создан» |
| GET | `/users/{userId}` | — | `200`, `UserResponse {id, email}` |

## Структура проекта

```
project/
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/com/example/swagger
    │   │   ├── SwaggerApplication.java        # точка входа
    │   │   ├── controller/UserController.java # REST-контроллер /users
    │   │   ├── dto/UserRequest.java           # DTO запроса (name, email)
    │   │   ├── dto/UserResponse.java          # DTO ответа (id, email)
    │   │   └── service/UserService.java       # логика (заглушка по методичке)
    │   └── resources/application.yml          # порт и настройки springdoc
    └── test/java/com/example/swagger
        ├── controller/SwaggerDocsTest.java        # /v3/api-docs, .yaml, /swagger-ui.html
        ├── controller/UserControllerTest.java     # интеграционные тесты REST-методов
        ├── controller/UserControllerUnitTest.java # модульные тесты контроллера (Mockito)
        └── service/UserServiceTest.java           # модульные тесты сервиса
```
