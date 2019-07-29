include "console.iol"
include "bankInterface.iol"

outputPort BankService {
    Location: "socket://localhost:8000"
    Protocol: soap
    Interfaces: BankInterface
}

main {
request.name="gino";
request.amount=10;
getToken@BankService(request)(token);
println@Console("token: " + token.sid)();
request1.sid=token.sid;
request1.name="gino";
verifyToken@BankService(request1)(success);
println@Console("Success: " + success.success)();
request2.name="gino";
refound@BankService(request2)(response);
println@Console("Success: " + response.success)()
}
