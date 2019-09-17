<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Restaurant</title>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>


    <script type='text/JavaScript'>

        //var menu_size=1;

        function rowsAdd() {
            //menu_size++;
            $("#new-menu").append(
                " <tr ><td contenteditable='true' >Ravioli</td>" +
                " <td contenteditable='true' >10</td></tr>"
            );
        }

        function showOption() {
            if (!document.getElementById("restName").value) {
                $('#info').html("Inserisci il nome del tuo ristorante");
            } else {
                document.getElementById('restName').readOnly = true;
                var d = new Date();
                var n = d.getHours();
                /* if ((n >= 10) && (n <= 23)) {
                     $('#fourth').show();
                 } else {
                */
                $('#second').show();
                $('#third').show();
                //}
            }
        }

        function sendAvailability() {

            console.log("Ristorante comunica disponibilita");

            var aval = document.getElementById("availability");
            var isAvailable = aval.options[aval.selectedIndex].value;
            var availabilityBody =
                {
                    "name": document.getElementById("restName").value.toString(),
                    "is_available": isAvailable.toString()
                };

            console.log("Inviando disponibilita ad acme-ws " + JSON.stringify(availabilityBody));

            var avalability_url = "http://localhost:8080/acmeat-ws/change-availability";

            var xhr = new XMLHttpRequest();
            xhr.open("PUT", avalability_url, true);
            xhr.setRequestHeader("Content-type", "application/json");
            var params = JSON.stringify(availabilityBody);
            xhr.send(params);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        console.log("xhr done successfully");
                        var resp = xhr.responseText;
                        console.log(resp);
                        $('#info2').html(resp);
                    } else {
                        console.log("xhr failed with " + xhr.status);
                    }
                } else {
                    console.log("xhr processing going on");
                }
            }
            console.log("request sent ");
        }

        function sendMenu() {
            console.log("Ristorante comunica menu");
            var dishes = [];
            var table = document.getElementById('new-menu');
            var rows = table.getElementsByTagName('tr');
            var allEmpty = true;
            for (var i = 1; i < rows.length; i++) {
                var cells = rows[i].getElementsByTagName('td');
                var piatto = cells[0].innerHTML;
                var piattoC = piatto.replace("<br>", "");
                var prezzo = cells[1].innerHTML;
                var prezzoC = prezzo.replace("<br>", "");
                if (!(piattoC == '' || piattoC == null || prezzoC == null || piattoC == "")) {
                    allEmpty = false;
                    var singleDish = {};
                    singleDish.name = cells[0].innerHTML;
                    singleDish.price = cells[1].innerHTML;
                    dishes.push(singleDish);
                }
            }
            if (!allEmpty) {
                var menuBody =
                    {
                        "name": document.getElementById("restName").value.toString(),
                        "menu": dishes
                    };
                console.log(menuBody);

                var menu_url = "http://localhost:8080/acmeat-ws/change-menu";

                var xhr = new XMLHttpRequest();
                xhr.open("PUT", menu_url, true);
                xhr.setRequestHeader("Content-type", "application/json");
                var params = JSON.stringify(menuBody);
                xhr.send(params);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            console.log("xhr done successfully");
                            var resp = xhr.responseText;
                            console.log(resp);
                            $('#info4').html(resp);
                        } else {
                            console.log("xhr failed with " + xhr.status);
                        }
                    } else {
                        console.log("xhr processing going on");
                    }
                }
                console.log("request sent");
                $('#info3').html("");
            } else {
                console.log("empty table");
                $('#info3').html("Inserisci almeno un nuovo piatto nel menu");
            }
        }

    </script>

</head>
<body>

    <h2>Benvenuto in AcmEat ristoratore</h2>

    <div id="first">
        Inserisci il nome del tuo ristorante:<br>
        <input type="text" id="restName" name="restName" value="default"> <input type="submit" value="Fatto"
                                                                                 onclick="showOption()">
        <div id="info" style="color:red"></div>
        <br>
    </div>
    <div id="second" hidden="true">
        Comunica se oggi sarai aperto:
        <select id="availability" name="availability">
            <option value="true" selected>SI</option>
            <option value="false">NO</option>
        </select><input type="button" onclick="sendAvailability()" value="Comunica ad AcmEat">
        <div id="info2" style="color:blue"></div>
        <br>
    </div>
    <div id="third" hidden="true">
        Comunica il tuo nuovo menu di oggi:
        <table id="new-menu" border="1px black">
            <tr>
                <td>Piatto</td>
                <td>Prezzo</td>
            </tr>
        </table>
        <button type="button" onclick="rowsAdd();">Aggiungi un piatto</button>
        <br>
        <input type="submit" onclick="sendMenu()" value="Comunica ad AcmEat">
        <div id="info3" style="color:red"></div>
        <div id="info4" style="color:blue"></div>
        <br>
    </div>
    <div id="fourth" style="color:blue">
        Ricorda che tra le 10 e le 23.59 non puoi apportare modifiche alla tua disponibilta o al menu.
    </div>
</body>
</html>