<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Client failure</title>

    <script type='text/JavaScript'>
        function goHome(){
            var url="http://localhost:8080/acmeat-frontend/client-home";
            window.location=url;
        }
    </script>

</head>
<body>
Payment failed

<div><input type="submit" value="Back to homepage" onclick="goHome()"></div>
</body>
</html>
