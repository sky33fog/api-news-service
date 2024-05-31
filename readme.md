
# API-news-service


## Описание приложения
Проект является практической работой 4 модуля курса "Разработка на Spring Framework" 
образовательной платформы Skillbox.

Проект представляет собой REST API приложение, реализующее основные функции новостного сервиса, которое через HTTP-запросы позволяет:
- создавать пользователей и управлять ими;
- создавать категории новостей и управлять ими;
- создавать новости и управлять ими;
- создавать комментарии для новостей и управлять ими. 

### Описание логики работы сервиса:
1. Возврат списка сущностей каждого контроллера осуществляется только с помощью пагинации.
  - ![img.png](readme_img/img6.png)

Исключением является возврат списка комметнариев.
В API реализован возврат списка комментариев только для одной конкретной новости.
  - ![img.png](readme_img/img5.png)
2. При запросе списка новостей, ответ содержит только поле отражающее количество комментариев для данной новости.
При запросе одной конкретной новости, ответ содержит список всех комментариев запрошенной новости.
3. Реализована фильтрация списка возвращаемых новостей по имени автора и/или имени категории.
4. Редактирование и удаление новостей и комментариев разрешается только пользователям, которые их создали:
- ![img.png](readme_img/img3.png)
- ![img.png](readme_img/img4.png)
- ![img.png](readme_img/img.png)
- ![img.png](readme_img/img2.png)
5. Реализована обработка ошибок, которые могут быть вклиентских запросах, с возвратом клиенту результата с пояснениями.
6. Наглядное отображение контроллеров и их методов реализовано с помощью **SpringDoc OpenAPI Starter WebMVC UI**,
доступно (после запуска приложения) по ссылке: http://localhost:8080/swagger-ui/index.html#/

## Настройка приложения
Параметры подключения к базе данных приложения задаются в конфигурационном файле **application.yml**.

По умолчанию файл содержит настройки для подключения к тестовой базе данных создаваемой скрипт-файлом **docker-compose.yml**


## Запуск приложения осуществляется средствами среды разработки
Для запуска приложения c тестовой базой данных необходимо выполнить последоватьельность действий: 
- если на машине отсутствует docker-образ _postgres 12.3_ - скачать его командой:
  - _**docker pull postgres:12.3**_
- запустить базу данных для работы приложения командой:
  - _**docker compose up**_ из директория **docker**, проекта;
- запустить приложение средствами среды разработки.