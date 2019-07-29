# Distance service

Simple distance service  implementation using graphhopper api.

## REST API



| HTTP Method           | Action              | Description                              |
| --------------------- | ------------------- | ---------------------------------------- |
| GET                  | [GetDistance](#getdistance) | Retrieve a specific order. |


## GetDistance

### Request
`http://localhost:9000/getDistance?from=Bologna&to=Milano`

- `from`:  The URL encoded **origin** address
- `to`: The URL encoded **destination** address 


### Response
The service will return a JSON response by means of the following schema:

```JSON
{
  "message": "A message describing the result",
  "distance": "The distance from origin to destination in meters"
}
```

## How to setup

1. Insert your api key in package.json

2. Build a docker image:

`docker build --tag=gis .`

3. Run the container:

`docker run -d -p <system_port>:7778 --name gis gis`

## How to test

Import *GisService.postman_collection.json* in Postman.