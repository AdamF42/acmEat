# Delivery Service

Simple restaurant service implementation.

## REST API

| HTTP Method           | Action              | Description                              |
| --------------------- | ------------------- | ---------------------------------------- |
| Get                  | [GetRestaurants](#GetRestaurants) | Get list of available restaurants in selected city.|
| POST                  | [SendOrder](#SendOrder) | Send order.|
| PUT                  | [ConfirmOrder](#ConfirmOrder) | Send Order confirmation |
| PUT                  | [AbortOrder](#AbortOrder) | Abort a apecific order |
| PUT                  | [ChangeAvailability](#ChangeAvailability) | Change availability of specified restaurant |
| PUT                  | [ChangeMenu](#ChangeMenu) | Change menu of specified restaurant |

## GetRestaurants

### Request

`http://localhost:8080/acmeat-ws/get-restaurants?city={city}` 

### Response

``` JSON
{
    "restaurants": [
        {
            "name": "Ciccio",
            "menu": [
                {
                    "name": "Ravioli di zucca",
                    "price": "5"
                }
            ],
            "city": "Via San Mamolo, 5, 40136 Bologna BO",
            "url": "http://localhost:10005/restaurant/",
            "is_open": true
        },
        {
            "name": "Yoma",
            "menu": [
                {
                    "name": "Ravioli",
                    "price": "5"
                },
                {
                    "name": "Sushi",
                    "price": "3"
                }
            ],
            "city": "Via Emilia Ponente, 72, 40133 Bologna BO",
            "url": "http://localhost:10006/restaurant/",
            "is_open": true
        },
        {
            "name": "debug",
            "menu": [
                {
                    "name": "Ravioli",
                    "price": "5"
                },
                {
                    "name": "Sushi",
                    "price": "3"
                }
            ],
            "city": "Piazza Camillo Benso Conte di Cavour, 1/d/e, 40124 Bologna BO",
            "url": "http://localhost:10006/restaurant/",
            "is_open": true
        }
    ],
    "result": {
        "status": "success",
        "message": ""
    }
}
```

## SendOrder

`http://localhost:8080/acmeat-ws/send-order` 

### Request

``` JSON
{
	"restaurant": "debug",
	"delivery_time":"20:15",
	"dishes": [{
                    "name": "Lasagne",
                    "price": "5"
                }],
	"from":"Via delle tovaglie 11",
	"to": "Via della salute 54"
}
```

### Response

``` JSON
{
    "bank_url": "http://localhost:10001/bank/",
    "total_price": "5.4160570563885795",
    "result": {
        "status": "success",
        "message": ""
    }
}
```

## ConfirmOrder

`http://localhost:8080/acmeat-ws/confirm?token={token}` 

### Response

``` JSON
{
    "result": {
        "status": "success",
        "message": ""
    }
}
```

## AbortOrder

`http://localhost:8080/acmeat-ws/abort` 

### Response

``` JSON
{
    "result": {
        "status": "success",
        "message": ""
    }
}
```

## ChangeAvailability

`http://localhost:8080/acmeat-ws/change-availability` 

### Request

``` JSON
{
    "name": "Ciccio",
    "is_available": "true"
}
```

### Response

``` JSON
{
    "result": {
        "status": "success",
        "message": ""
    }
}
```

## ChangeMenu

`http://localhost:8080/acmeat-ws/change-menu` 

### Request

``` JSON
{
    "name": "Ciccio",
    "menu": [
        {
            "name": "Ravioli",
            "price": "1"
        },
        {
            "name": "Ciccioli",
            "price": "12"
        },
        {
            "name": "Melanzale grigliate",
            "price": "14"
        }
    ]
}
```

### Response

``` JSON
{
    "result": {
        "status": "success",
        "message": ""
    }
}
```

## How to test

Import postman collection and evironment in Postman.

