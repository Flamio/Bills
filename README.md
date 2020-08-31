Bills

При первом запуске срабатывает миграция, которая наполняет БД первоначальными данными.

Примеры запросов

Списание со счета

POST /bills/debit/
{
    "driverId": 1,
    "billId":1,
    "sum": 100
}

начисление на счет

POST /bills/kredit/
{
    "driverId": 1,
    "billId":1,
    "sum": 100
}

оборот по счету 

POST /bills/turnover
{
    "startDate": "2020-08-27T10:00:00",
    "endDate": "2020-08-27T23:00:00",
    "driverId": 1,
    "billId":1
}

Просмотр операций постранично

POST /bills/history?page=0&size=4
{
    "startDate": "2020-08-27T10:00:00",
    "endDate": "2020-08-27T23:00:00",
}

Перевод между счетами

POST /bills/transfer
{
    "driverId": 1,
    "billId":1,
    "destinationBillId": 2,
    "sum": 100
}

Получение списка всех водителей и их счетов

GET /drivers/list

Получение текущей суммы по счету

GET /drivers/1/bills/1 

