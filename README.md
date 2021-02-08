# Сетевой чат
*Сетевой чат. Финальный проект курса GeekBrains Java Core.*

### Технические характеристики
- IDE: Idea
- Управление проектом: Maven
- Управление версиями: github

## Функциональность

#### Регистрация
- Пользователь может зарегистрироваться, но если есть пользователь с таким именем, регистрация не удастся.

#### Авторизация
- Войдите, используя свое имя пользователя и пароль. После входа в систему вы сможете войти на сервер.

#### Личные сообщение
- Вы также можете отправить личное сообщение пользователю, а также получить личное сообщение от пользователя.

#### Логи
- На стороне сервера сервер может отслеживать всю информацию.

#### Запуск/Остановка сервера
- В окне сервера сервер можете запустить или закрыть сервер вручную.

#### База данных
- Все пользовательские данные хранятся в базе данных.
- Все сообщения пользователей будут записываться в базу данных, в том числе личные.
___
- Вы можете изменить цвет сообщения.
- Вы можете отключить уведомления.
- Вы можете сохранить сообщение журнала.
- Вы можете изменить свое имя в чате, и все пользователи увидят, что вы изменили свое имя.
___
- Графическая часть реализована с использованием Swing.
- Данные учетной записи и история чата хранятся на стороне сервера с помощью SQLite.
- Работа с базой данных основана на JDBC.
- В проекте используются зависимости от репозитория Maven.
---

## Чат
- ![Chat](src/resource/images/Chat.jpg)

---

## Сервер
- ![Server](src/resource/images/Server.jpg)

