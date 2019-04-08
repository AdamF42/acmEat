# Delivery Service

Simple restaurant service implementation.

## REST API

| HTTP Method           | Action              | Description                              |
| --------------------- | ------------------- | ---------------------------------------- |
| POST                  | [PlaceOrder](#place) | Post a new order resource. |
| GET                  | [GetOrder](#get) | Retrieve a specific order. |
| PUT                  | [UpdateOrder](#update) | Update a specific order |
| PUT                  | [ConfirmOrder](#confirm) | Confirm a specific order |
| PUT                  | [AbortOrder](#abort) | Abort a apecific order |


## Place

`http://<host>:<port>/delivery/order`

### Request

```JSON
{
    "srcAddress": "A",
    "destAddress": "B",
    "deliveryTime": "11"
}
```

### Response

```JSON
{
    "id": 1
}
```

## Get

`http://<host>:<port>/delivery/order/<id>`

### Response
```JSON
{
    "deliveryTime": "11",
    "destAddress": "B",
    "id": 1,
    "srcAddress": "A",
    "status": "pending"
}
```

## Update

`http://<host>:<port>/delivery/order/<id>`

### Request

```JSON
{
    "deliveryTime": "11",
    "destAddress": "B",
    "id": 1,
    "price": 5,
    "srcAddress": "A",
    "status": "available"
}
```

### Response

```JSON
{
    "deliveryTime": "11",
    "destAddress": "B",
    "id": 1,
    "price": 5,
    "srcAddress": "A",
    "status": "available"
}
```

## Confirm

 `http://<host>:<port>/delivery/order/<id>/status/confirmed`

### Response

```JSON
{
    "deliveryTime": "11",
    "destAddress": "B",
    "id": 1,
    "price": 5,
    "srcAddress": "A",
    "status": "confirmed"
}
```

## Abort

 `http://<host>:<port>/delivery/order/<id>/status/aborted`


### Response

```JSON
{
    "deliveryTime": "11",
    "destAddress": "B",
    "id": 1,
    "price": 5,
    "srcAddress": "A",
    "status": "aborted"
}
```

## How to setup

Build a docker image:

`docker build --tag=delivery . `

Run the container:

`docker run -d -p <system_port>:<container_port> --name delivery delivery`


## How to test

Import *DeliveryService.postman_collection.json* in Postman.

<!-- TODO: add descriptions-->