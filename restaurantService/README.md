# Restaurant Service
Simple restaurant service implementation.

## REST API

| HTTP Method           | Action                | Description                              |
| --------------------- | --------------------- | ---------------------------------------- |
| POST                  | [PlaceOrder](#place)  | `` |
| GET                   | [GetOrder](#get)      | `` |
| PUT                   | [AbortOrder](#abort)  | `` |
| PUT                   | [GetAvailability](#availability)  | `` |



## Place

### Request

`http://<host>:<port>/restaurant/order`

```JSON
{
  "content": [
                "pizza",
                "carbonara"
             ],
   "delivery_time": "13"
}
```

### Response

```JSON
{ 
  "id" : "1",
  "content": [
                "pizza",
                "carbonara"
             ],
   "delivery_time": "13",
   "status": "accepted"
}
```

## Get

### Request
`http://<host>:<port>/restaurant/order/<order_id>`
### Response
```JSON
{
  "id" : "1",
  "content": [
                "pizza",
                "carbonara"
             ],
  "delivery_time": "13",
  "status": "accepted" //It could be null
}
```


## Abort

### Request

`http://<host>:<port>/restaurant/order/abort`


```JSON
{
  "id" : "1",
  "content": [
                "pizza",
                "carbonara"
             ],
   "delivery_time": "13",
   "status": "accepted"
}
```
### Response
```JSON
{
  "id" : "1",
  "content": [
                "pizza",
                "carbonara"
             ],
  "delivery_time": "13",
  "status": "aborted"
}
```

### Request

`http://<host>:<port>/restaurant/availability`


```JSON
{
  "content": [
                "pizza",
                "carbonara"
             ]
}
```
### Response
```JSON
{
    "content": [
        "pizza",
        "carbonara"
    ],
    "delivery_time": null,
    "id": null,
    "status": "AVAILABLE" // NOT_AVAILABLE
}
```

## How to setup

docker build --tag=restaurant .

docker run -d -p 5000:5000 --name restaurant restaurant

## How to test
Run the postman collection in this folder