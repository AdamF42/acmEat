# Delivery Service

Simple restaurant service implementation.

## REST API

| HTTP Method           | Action              | Description                              |
| --------------------- | ------------------- | ---------------------------------------- |
| POST                  | [GetAvailability](#getavailability) | Create a new order resource and check for delivery availability.|
| GET                  | [GetOrder](#getorder) | Retrieve a specific order.|
| PUT                  | [SendOrder](#sendorder) | Update a specific order |
| PUT                  | [AbortOrder](#abort) | Abort a apecific order |

## GetAvailability

`http://<host>:<port>/delivery/order` 

### Request

``` JSON
{
	"company": "debug",
	"delivery_time":"20:15",
	"src_address":"Via delle tovaglie 13",
	"dest_address": "Via della salute 54"
}
```

### Response

``` JSON
{
    "id": 1,
    "price": 0.09115258233753853,
    "company": "debug",
    "delivery_time": "20:15",
    "dest_address": "Via della salute 54",
    "src_address": "Via delle tovaglie 13",
    "status": "AVAILABLE"
}
```

## GetOrder

`http://<host>:<port>/delivery/order/<id>` 

### Response

``` JSON
{
    "id": 1,
    "price": 0.09115258233753853,
    "company": "debug",
    "delivery_time": "20:15",
    "dest_address": "Via della salute 54",
    "src_address": "Via delle tovaglie 13",
    "status": "ACCEPTED"
}
```

## SendOrder

`http://<host>:<port>/delivery/order/<id>` 

### Request

``` JSON
{
	"id": 1
}
 ```

### Response

``` JSON
{
    "id": 1,
    "price": 0.09115258233753853,
    "company": "debug",
    "delivery_time": "20:15",
    "dest_address": "Via della salute 54",
    "src_address": "Via delle tovaglie 13",
    "status": "ACCEPTED"
}
```

## Abort

`http://<host>:<port>/delivery/order/<id>/status/aborted` 

### Response

``` JSON
{
    "id": 1,
    "price": 0.09115258233753853,
    "company": "debug",
    "delivery_time": "20:15",
    "dest_address": "Via della salute 54",
    "src_address": "Via delle tovaglie 13",
    "status": "ABORTED"
}
```

## How to run

Build a docker image:

`docker build --tag=delivery . ` 

Run the container:

`docker run -d -p <system_port>:<container_port> --name delivery delivery` 

## How to test

Import postman collection and evironment in Postman.

