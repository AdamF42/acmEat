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


## How to create the WSDL:

Launch:

wsdl_creator.bat

## How to setup:

Build a docker image:

docker build --tag=bank .

Run the container:

docker run -itd -p 8000:8000 --name bank --hostname bank bank

## How to test:

Import BankService-soapui-project.xml in SoapUI
