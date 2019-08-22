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
  TokenResponse.sid
  ClientToken.sid
}

init
{
  keepRunning = true;
  println@Console("Server ON")()
}

main {
  [ getToken( request )( response ) {
    println@Console("User: "+request.name)();
    response.sid = csets.sid = new;
    println@Console("Token: "+response.sid)()
  }]

[ verifyToken( request )( response ) {
    // check that the payment is possible, money
    random@Math( )( res );
    if (res<0.5){
        response.success=true
    }else{
        response.success=false
    };
    println@Console("Token verified")()
}]

[ refound( request )( response ) {
  // check that the refound is possible
  random@Math( )( res );
  if (res<0.5){
      response.success=true
  }else{
      response.success=false
  };
  println@Console("Refound processed")()
}]


  /* {
    while( keepRunning ){
      [ whithdraw( request )( result ) {
        if (global.users.(username).balance >= request.message) {
          result.value=global.users.(username).balance=global.users.(username).balance-request.message;
          result.status="SUCCESS"
        }
        else {
          result.value=global.users.(username).balance;
          result.status="FAILURE"
        };
        println@Console("USER: "+username+" Request: Whithdraw "+request.message )()
      }]

      [ deposit( request )( result ){
        result.value=global.users.(username).balance=global.users.(username).balance+request.message;
        result.status="SUCCESS";
        println@Console("USER: "+username+" Request: Deposit "+request.message )()
      }]

      [ report( request )( result ){
        println@Console( "USER: "+username+" Request: Report")();
        result.value=global.users.(username).balance;
        result.status="SUCCESS"
      }]

      [ logout( request ) ] {
        println@Console("Sid: "+username+" Request: Logout")();
        keepRunning = false
      }
    }
  }*/
  /////////////////// REGISTRATION ///////////////////
  /*[ register( request )( response ) {
    username = request.username;
    password = request.password;
    println@Console("user: " + username + " registration")();

    if ( !is_defined( global.users.( username ) ) ) {
      println@Console("user: " + username + " not found")();
      global.users.( username ).balance = 0;
      global.users.( username ).password = password;
      response.sid = csets.sid = new;
      response.status = "SUCCESS"
    }else{
      println@Console("user: " + username + " found")();
      response.sid = " ";
      response.status = "FAILURE";
      keepRunning=false
    };
    println@Console("USER "+username+" Request: Registration")()
  }] {

    while( keepRunning ){

      [ whithdraw( request )( result ) {
        if (global.users.(username).balance >= request.message) {
          result.value=global.users.(username).balance=global.users.(username).balance-request.message;
          result.status="SUCCESS"
        }
        else {
          result.value=global.users.(username).balance;
          result.status="FAILURE"
        };
        println@Console("USER: "+username+" Request: Whithdraw "+request.message )()
      }]

      [ deposit( request )( result ){
        result.value=global.users.(username).balance=global.users.(username).balance+request.message;
        result.status="SUCCESS";
        println@Console("USER: "+username+" Request: Deposit "+request.message )()
      }]

      /*[ report( request )( result ){
        println@Console( "USER: "+username+" Request: Report")();
        result.value=global.users.(username).balance;
        result.status="SUCCESS"
      }]*/

      /*[ logout( request ) ] {
        println@Console("Sid: "+username+" Request: Logout")();
        keepRunning = false
      }
    }
  }*/
}
