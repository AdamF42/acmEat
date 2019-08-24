# Bank service

## Interface

| Name     | Input type                          | Output type                           |
| -------- | ----------------------------------- | ------------------------------------- |
| getToken  | [ClientTokenRequest](#clienttokenrequest)   |         [TokenResponse](#tokenresponse)         |                              |
| verifyToken     | [ClientToken](#clienttoken)         | [SuccessResponse](#successresponse)         |
| login    | [RefoundRequest](#refoundrequest)       | [SuccessResponse](#successresponse)       |

## Types

### ClientTokenRequest

```json
{
  "name": "string",
  "amount?": "double"
}
```

### TokenResponse

```json
{
  "sid": "string"
}
```

### ClientToken

```json
{
  "sid": "string",
  "name?":"string"
}
```

### SuccessResponse

  ```json
  {
    "success":"bool"
  }
  ```

### RefoundRequest

 }
 ```json
 {
   "name":"string",
   "message": "int",
   "status": "string"
 }
 ```

## Rest interface

## How to create the WSDL:

Launch:

jolie2wsdl  --namespace soseng --portAddr http://localhost:8000  --portName Bank --o bankService.wsdl ./src/server.ol

## How to setup:

Build a docker image:

docker build --tag=bank .

Run the container:

docker run -itd -p 8000:8000 -p 8001:8070 --name bank --hostname bank bank

## How to test:

Import BankService-soapui-project.xml in SoapUI

