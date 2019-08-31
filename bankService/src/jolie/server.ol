include "bankInterface.iol"
include "console.iol"
include "string_utils.iol"
include "math.iol"

inputPort Bank {
  Location: "socket://localhost:8000"
  Protocol: soap {
    .wsdl = "bankService.wsdl";
    .wsdl.port = "BankPort"
  }
  Interfaces: BankInterface
}

execution{ concurrent }

cset {
 sid:
  GetTokenResponse.sid
  VerifyTokenRequest.sid
  RefoundRequest.sid
}

init
{
  println@Console("Server ON")()
}

main {
  [ getToken( request )( response ) {
    // check that the payment is possible, money
    response.sid = csets.sid = new;
    random@Math( )( res );
    if (res<0.5 ||  request.name=="debug"){
          response.status="success"
          global.users.( response.sid ).result = true
          global.users.(request.sid).isRefounded = false
    }else{
          response.status="failure"
          global.users.( response.sid ).result = false
          global.users.(request.sid).isRefounded = true
    } 
    println@Console("GetToken processed")()   
  }]

  [ verifyToken( request )( response ) {
      response.success=global.users.(request.sid).result
      println@Console("VerifyToken processed")()
  }]

  [ refound( request )( response ) {
    if (!global.users.(request.sid).isRefounded){
      global.users.(request.sid).isRefounded = true
      global.users.(request.sid).result = false
    }
    response.success=global.users.(request.sid).result
    println@Console("Refound processed")()
  }]
}
