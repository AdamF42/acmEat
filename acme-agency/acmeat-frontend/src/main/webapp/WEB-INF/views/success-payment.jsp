<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Client success</title>
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
    </script>
</head>
<body>
Payment successful

<div id="fourth">


    <div><input type="submit" value="Cancella ordine" onclick="cancelOrder()"></div>
    <div id="canc"></div>

</div>


</body>
</html>
