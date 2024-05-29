# REST API новостного сервиса

## Обзор
Этот REST API предоставляет доступ к новостному сервису, позволяя пользователям получать, создавать, обновлять и удалять новостные статьи и оставлять комментарии к ним. 

## Базовый URL
/api/


### Новости

#### Получить все статьи
GET /news

Получить список всех новостных статей.

**Параметры:**
- `pageSize` : Количество статей на странице.
- `pageNumber` : Номер страницы для пагинации.

**Ответ:**
```json
{
    "news": [
        {
            "id": id новости,
            "newsText": "Текст новости",
            "numOfComments": колличество комментариев 
        },
    ]
}
```
#### Получить статью по ID

GET /news/{id}

Получить конкретную статью по её ID.

**Ответ:**
```json
{
    "id": id новости,
    "newsText": "Текст новости",
    "comments": [список комментариев]
}
```

#### Создать новую статью
POST /news

Создать новую новостную статью.

**Тело запроса:**
```json
{
    "newsText": "Текст новости",
    "userId": id пользователя,
    "categoryId": id категории
}
```
**Ответ:**
```json
{
    "id": id новости,
    "newsText": "Текст новости",
    "comments": [список комментариев]
}
```
#### Обновить статью
PUT /news/{id}

Обновить существующую статью по её ID.

**Параметры:**
- `UserId` : id пользователя создавший запись.

**Тело запроса:**
```json
{
    "newsText": "Текст новости",
    "userId": id пользователя,
    "categoryId": id категории
}
```
**Ответ:**
```json
{
    "id": 7,
    "newsText": "Текст новости",
    "comments": [список комментариев]
}

```
#### Удалить статью
DELETE /articles/{id}

Удалить статью по её ID.

**Параметры:**
- `UserId` : id пользователя создавший запись.
```json
Ответ:
{
}
```
#### Получить список статей с фильтрацией

GET /news/

Получить список с фильтрацией новостей по категориям и авторам.

**Параметры:**
- `userId` : id пользователя создавший запись.
- `categoryId` : id категории.
**Ответ:**
```json
{
  "news": [
    {
      "id": id новости,
      "newsText": "Текст новости",
      "numOfComments": колличество комментариев
    },
  ]
}
```
### Пользователь, комментарий, категории
**используя url /user, /comment, /category реализованы API оставшихся сущностей**


### Обработка ошибок
Ошибки возвращаются с соответствующими HTTP статус-кодами и JSON ответом.

Пример ответа с ошибкой:
```json
{
"errorMessage": "Пользователь с ID 1 не может редактировать эту новость!"
}
```