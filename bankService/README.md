# Bank service

## SOAP Interface

| Name     | Input type                          | Output type                           |
| -------- | ----------------------------------- | ------------------------------------- |
| getToken  | [GetTokenRequest](#gettokenrequest)   |         [GetTokenResponse](#gettokenresponse)         |                              |
| verifyToken     | [VerifyTokenRequest](#verifytokenrequest)         | [VerifyTokenResponse](#verifytokenresponse)         |
| login    | [RefoundRequest](#refoundrequest)       | [RefoundResponse](#refoundresponse)       |

## Types

### GetTokenRequest

``` xml
<it:getToken>
    <amount>1</amount>
    <name>debug</name>
</it:getToken>
```

### GetTokenResponse

``` xml
<getTokenResponse>
    <sid xsi:type="xsd:string">17e25d0d-3d1d-4d7f-96e7-5eeed4dc383d</sid>
    <status xsi:type="xsd:string">success</status>
</getTokenResponse>
```

### VerifyTokenRequest

``` xml
<it:verifyToken>
    <sid>17e25d0d-3d1d-4d7f-96e7-5eeed4dc383d</sid>
</it:verifyToken>
```

### VerifyTokenResponse

  

``` xml
<verifyTokenResponse>
    <success xsi:type="xsd:boolean">true</success>
</verifyTokenResponse>
  ```

### RefoundRequest

 

``` xml
<it:refound>
    <sid>17e25d0d-3d1d-4d7f-96e7-5eeed4dc383d</sid>
</it:refound>
 ```

### RefoundResponse

  

``` xml
<refoundResponse>
    <success xsi:type="xsd:boolean">true</success>
</refoundResponse>
  ```

## How to create the WSDL:

Launch:

jolie2wsdl  --namespace it.unibo --portAddr http://localhost:8000  --portName Bank --o bankService.wsdl ./src/server.ol

## How to setup:

Build a docker image:

`docker build --tag=bank .` 

Run the container:

`docker run -td -p 8000:8000 -p 10001:8070 --name bank --hostname bank bank` 

## How to test:

Import BankService-soapui-project.xml in SoapUI

