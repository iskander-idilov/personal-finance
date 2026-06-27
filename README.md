# AURUM — Personal Finance Tracker

**AURUM** — веб-приложение для управления личными финансами. Ведите учёт доходов и расходов, категоризируйте транзакции, отслеживайте котировки акций в реальном времени и читайте актуальные финансовые новости.

---

## Стек технологий

| Слой | Технология |
|---|---|
| Backend | Java 17, Spring Boot 4.0.6 |
| Безопасность | Spring Security (BCrypt, ролевая модель) |
| База данных | PostgreSQL 16 |
| Миграции | Liquibase |
| ORM | Spring Data JPA / Hibernate |
| Кэш | Redis 7 |
| Frontend | Thymeleaf + Thymeleaf Layout Dialect |
| Маппинг | MapStruct |
| Boilerplate | Lombok |
| Внешний API | Finnhub.io (акции и новости) |
| Контейнеризация | Docker / Docker Compose |
| Сборка | Gradle |

---

## Функциональность

### Аутентификация и авторизация
- Регистрация и вход через форму Spring Security
- Пароли хэшируются с помощью BCrypt
- Ролевая модель доступа: `USER` и `ADMIN`
- Страница `/access-denied` при попытке доступа к запрещённым разделам

### Дашборд (Главная страница)
- Текущий баланс счёта
- Сводка общих доходов и расходов
- Разбивка расходов по категориям
- Виджет котировок акций (AAPL, GOOGL, MSFT, TSLA, AMZN) — данные загружаются из Finnhub API и кэшируются при старте приложения

### Транзакции
- Добавление транзакций дохода или расхода с указанием суммы, типа и категории
- Редактирование и удаление существующих транзакций
- Постраничный вывод (10 записей на страницу)
- Поиск транзакций по ключевому слову
- Баланс счёта автоматически пересчитывается при каждом добавлении и удалении

### Категории
- Создание и управление пользовательскими категориями транзакций

### Финансовые новости
- До 20 последних финансовых новостей из Finnhub
- Заголовок, краткое описание, изображение и время публикации (часовой пояс Asia/Almaty)
- Кэшируются в памяти с асинхронным обновлением при старте

### Настройки профиля
- Смена пароля (с проверкой текущего и подтверждением нового)
- Смена email (с подтверждением паролем)

### Административная панель
- Просмотр всех зарегистрированных пользователей по адресу `/admin/users`
- Доступно только пользователям с ролью `ADMIN`

---

## Структура проекта

```
src/main/java/finance/
├── config/          # Безопасность, прогрев кэша Redis, конфигурация MVC, интерсепторы
├── controller/      # Веб-контроллеры (Home, Login, Register, Transactions, Categories, News, Settings, Admin)
├── dto/             # Data Transfer Objects + маппинг через MapStruct
├── entity/          # JPA-сущности (User, Account, Transaction, Category, Role, TransactionType)
├── repository/      # Spring Data репозитории + кастомные JPQL-запросы
├── security/        # CustomUserDetailsService
└── service/         # Интерфейсы и реализации сервисов (Account, Transaction, Category, Stock, News)

src/main/resources/
├── db/changelog/    # Скрипты миграций Liquibase
├── templates/       # HTML-шаблоны Thymeleaf
└── static/images/   # Статические ресурсы
```

---

## Запуск

### Требования
- Docker и Docker Compose
- Java 17 (для локальной разработки без Docker)
- Gradle

### Запуск через Docker (полный стек)

```bash
docker-compose up --build
```

Приложение будет доступно по адресу `http://localhost:8080`.

> **Важно:** после изменения исходного кода необходимо пересобрать проект:
> ```bash
> ./gradlew build
> docker-compose up --build
> ```

### Запуск для разработки (через IDE)

```bash
# Запустить только инфраструктуру
docker-compose up postgres redis

# Запустить приложение из IDE (рекомендуется IntelliJ IDEA)
```

---

## Конфигурация

Основные параметры в `application.properties`:

| Параметр | Значение по умолчанию |
|---|---|
| `server.port` | `8080` |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/BitLab` |
| `spring.data.redis.host` | `localhost` |
| `spring.data.redis.port` | `6379` |
| `finnhub.api.key` | API-ключ Finnhub |
| `finnhub.news.limit` | `20` |

---

## Миграции базы данных

Миграции управляются через Liquibase и применяются автоматически при запуске. Файлы миграций находятся в:

```
src/main/resources/db/changelog/changes/
```

Ручной запуск миграций через Gradle:

```bash
./gradlew update
```

---

## Тестирование

```bash
./gradlew test
```

Тесты используют отдельный экземпляр PostgreSQL (`localhost:5433`), описанный в `docker-compose.yml`, и отдельный файл `src/test/resources/application.properties`.

---

## Интеграция с внешним API

### Finnhub.io
- **Котировки акций:** `GET /api/v1/quote?symbol={ticker}&token={key}`  
  Отслеживаемые тикеры: `AAPL`, `GOOGL`, `MSFT`, `TSLA`, `AMZN`
- **Финансовые новости:** `GET /api/v1/news?category=general&token={key}`

Данные загружаются асинхронно при старте приложения и хранятся в volatile-кэше в памяти. Блокировка через `AtomicBoolean` предотвращает одновременные запросы на обновление.

---

## Автор

**Iskander Idilov**