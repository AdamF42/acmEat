<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Client</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type='text/JavaScript'>
        function cancelOrder(){

            console.log("Cancellando ordine");

            var url="http://localhost:8080/acmeat-ws/abort";
            var xhr = new XMLHttpRequest();
            xhr.open("PUT",url, true);
            xhr.send();

            xhr.onreadystatechange = function(){
                if (xhr.readyState === 4){
                    if (xhr.status === 200){
                        console.log("xhr done successfully");
                        var resp = xhr.responseText;
                        console.log(resp);
                        $('#canc').html("Il tuo ordine e stato cancellato");
                    } else {
                        console.log("xhr failed");
                    }
                } else {
                    console.log("xhr processing going on");
                }
            }
            console.log("request sent succesfully");
        }

        function sendToken(){

            console.log("Cliente invia token ad acme");

            var token="a";//TODO:take it from window.location, splitto per & e levo token=, la parte dopo è il token
            //http://localhost:8080/acmeat-frontend/client-after-payment?status=success&token=f357b821-cfa2-445b-8a1b-b52b1cc17f3e

            var url="http://localhost:8080/acmeat-ws/confirm?token="+token;
            var xhr = new XMLHttpRequest();
            xhr.open("PUT",url, true);
            xhr.send();

            xhr.onreadystatechange = function(){
                if (xhr.readyState === 4){
                    if (xhr.status === 200){
                        console.log("xhr done successfully");
                        var resp = xhr.responseText;
                        console.log(resp);
                        //TODO: verify succes or failure of the token verify
                        //if success
                        $('#first').hide();
                        $('#token-success').show();
                    } else {
                        console.log("xhr failed");
                    }
                } else {
                    console.log("xhr processing going on");
                }
            }
            console.log("request sent succesfully");
        }
    </script>
</head>
<body>
<div id="first">Hai completato il pagamento, ora confermalo ad Acme:
<input type="submit" value="Conferma pagamento" onclick="sendToken()"></div>


<div id="token-success"  hidden="true"><input type="submit" value="Cancella ordine" onclick="cancelOrder()">
<div id="canc"></div></div>
</body>
</html>
