# НТЦ Протей: Тестовое задание (Java)
<details>
<summary>Текст задания</summary>
<p>

> Написать серверную часть Web-приложения согласно следующим требованиям:
>
> Средства разработки: Java 1.7 (или выше)
> Инструменты: Spring Framework
> Протокол: HTTP
> База данных: PostgreSQL
>
> Примечание: Вместо обращения к реальной базе можно реализовать "заглушку" с имитацией обращения и задержкой по времени 5-10 сек.
>
> Функционал:
>
> * Добавление нового пользователя. Передаем на сервер данные пользователя (имя пользователя, email, phoneNumber и т.д.). Сохраняем информацию в базе данных. Ответ сервера — уникальный ID нового пользователя
> * Получение информации о пользователе. Передаем на сервер уникальный ID пользователя. Читаем информацию из базы данных. Ответ сервера — данные пользователя
> * Изменение статуса пользователя (Online, Away, Offline). Передаем на сервер уникальный ID пользователя и новый статус. Изменяем статус пользователя. Ответ сервера — уникальный ID пользователя, новый и предыдущий статус
> * Перевод в статус Away должен делаться автоматически через 5 минут после последнего изменения статуса на online. Таким образом, если “подтверждать” статус online пользователя каждые 5 минут - автоматического перехода в Away не должно быть
>
> Обязательные требования:
> * RESTful
> * Все данные в формате JSON
> * Обработка ошибок

</p>
</details>

## Зависимости
  * JDK 8
  * Tomcat 7+
  * PostgreSQL
  * Redis 5

## Описание программы
  * в файле `/resources/application.properties` задаются параметры соединения с PostgreSQL и Redis
  * файл `Protei.postman_collection.json` содержит экспортированные из Postman примеры запросов к API
  * формат запросов можно посмотреть по [ссылке](https://documenter.getpostman.com/view/4002121/SVYurH6E?version=latest)
  * endpoint `/accountrs/api/v1/accounts/:id` - получение данных, добавление пользователя
  * endpoint `/accountrs/api/v1/accounts/:id/status` - получение, обновление статуса пользователя