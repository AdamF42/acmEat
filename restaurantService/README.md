# Restaurant Service
Simple restaurant service implementation.

## REST API

| HTTP Method           | Action                | Description                              |
| --------------------- | --------------------- | ---------------------------------------- |
| PUT                  | [SendOrder](#sendorder)  | `` |
| GET                   | [GetOrder](#getorder)      | `` |
| PUT                   | [AbortOrder](#abort)  | `` |
| POST                   | [GetAvailability](#getavailability)  | `` |

## SendOrder

### Request

`http://<host>:<port>/restaurant/order` 

``` JSON
{
	"id": 1
}
```

### Response

``` JSON
{
   "id": 1,
   "delivery_time": "11",
   "dishes": [
      {
      "name": "Lasagne",
      "price": "5"
      }
   ],
   "from": "Via delle tovaglie 11",
   "restaurant": "Ciccio",
   "status": "AVAILABLE",
   "to": "Via della salute 54"
}
```

## GetOrder

### Request
`http://<host>:<port>/restaurant/order/<order_id>` 

### Response

``` JSON
{
  "delivery_time": "11",
  "dishes": [
    {
      "name": "Lasagne",
      "price": "5"
    }
  ],
  "from": "Via delle tovaglie 11",
  "id": 1,
  "restaurant": "debug",
  "status": "ACCEPTED",
  "to": "Via della salute 54"
}
```

## Abort

### Request

`http://<host>:<port>/restaurant/order/abort` 

``` JSON
{
  "id" : "1"
}
```

### Response

``` JSON
{
  "delivery_time": "11",
  "dishes": [
    {
      "name": "Lasagne",
      "price": "5"
    }
  ],
  "from": "Via delle tovaglie 11",
  "id": 1,
  "restaurant": "debug",
  "status": "ABORTED",
  "to": "Via della salute 54"
}
```

## GetAvailability

### Request

`http://<host>:<port>/restaurant/availability` 

``` JSON
{
	"restaurant": "Ciccio",
	"delivery_time":"11",
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
   "id": 1,
   "delivery_time": "11",
   "dishes": [
   {
   "name": "Lasagne",
   "price": "5"
   }
   ],
   "from": "Via delle tovaglie 13",
   "restaurant": "Ciccio",
   "status": "AVAILABLE",
   "to": "Via della salute 54"
}
```

## How to setup

docker build --tag=restaurant .

docker run -d -p 5000:5000 --name restaurant restaurant

## How to test

Run the postman collection in this folder

