<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Client</title>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/vue"></script>

</head>
<body>

<h2>Benvenuto in AcmEat</h2>

<div id="first">

    Inserisci il comune in cui vuoi ordinare:<br>


    <select id="comune" name="comune">
        <option value="Bologna" selected>Bologna</option>
        <option value="Milano">Milano</option>
        <option value="Bolzano">Bolzano</option>
    </select><br>
    <br><br>
    <input type="submit"  value="Cerca ristoranti vicino a me" onclick="searchRestaurant()" id="cercaR">


</div>

<br>
<div id="info"></div>
<br>

<div id="second" hidden="true">
    Procedi con l'ordine:

    Seleziona un ristorante:
    <select id="ristorante" name="ristorante">

    </select><br>


    <select id="piatti" name="piatti" multiple >

    </select>

    <div id="info2"  style="color:red"></div>

    <br><br>

    Inserisci l'orario di consegna, entro le 23:59:  <br>
    Ora :    <label id="demo"></label><input type="range" min="10" max="23" value="12"  step ="1" class="slider" id="myRange"><br>
    Minuto : <label id="demo1"></label><input type="range" min="0" max="59" value="30"  step ="1" class="slider" id="myRange1">
    <div id="info3"  style="color:red"></div>

    <br><br>

    Inserisci l'indirizzo di consegna:
    <input type="text" id="indirizzo" name="indirizzo" value="">
    <div id="info4"  style="color:red"></div>

    <br><br>

    <input type="submit" id="ordButton" onclick="confirmOrder()" value="Ordina">

</div>

<br><br>

<div id="third" hidden="true">

    Inserisci il numero della carta di credito:<br>
    <input type="text" id="numero" name="numero" value=" ">
    <br>
    <br>
    <div><input type="submit" value="Conferma pagamento" onclick="pay()"></div>


</div>

<br><br>

<div id="fourth" hidden="true">


    <div><input type="submit" value="Cancella ordine" onclick="cancelOrder()"></div>
    <div id="canc"></div>

</div>

<script type='text/JavaScript'>
    var d = new Date();
    var n = d.getHours();

    var slider = document.getElementById("myRange");
    slider.min=n;
    var output = document.getElementById("demo");
    output.innerHTML = slider.value; // Display the default slider value
    // Update the current slider value (each time you drag the slider handle)
    slider.oninput = function() {
        output.innerHTML = this.value;
    }

    var slider1 = document.getElementById("myRange1");
    slider1.min=n;
    var output1 = document.getElementById("demo1");
    output1.innerHTML = slider.value; // Display the default slider value

    // Update the current slider value (each time you drag the slider handle)
    slider1.oninput = function() {
        output1.innerHTML = this.value;
    }





</script>

<script type='text/JavaScript'>

    function searchRestaurant() {

        var e = document.getElementById("comune");
        var city = e.options[e.selectedIndex].text;

        var restaurant_url="http://localhost:8080/acmeat-ws/get-restaurants?city=".concat(city);

        var xhr = new XMLHttpRequest();
        xhr.withCredentials=true;
        xhr.open("GET", restaurant_url, true);
        xhr.send();
        xhr.onreadystatechange = function(){
            if (xhr.readyState === 4){
                if (xhr.status === 200){
                    console.log("xhr done successfully");
                    var resp = xhr.responseText;
                    var respParsed = JSON.parse(resp).restaurants;
                    console.log(respParsed);
                    //TODO, gestire ingresso fuori orario, vedi nel messaggio di risposta
                    if(respParsed.length>0){
                        $('#info').html("");
                        var elenco =  document.getElementById('ristorante');
                        for( i=0; i<respParsed.length; i++) {
                            var option = document.createElement("option");
                            option.text = respParsed[i].name;
                            option.value = respParsed[i].name;
                            elenco.append(option);
                        }
                        var elencoPiatti =  document.getElementById('piatti');
                        elencoPiatti.innerText="";
                        for( i=0; i<respParsed[0].menu.length; i++) {
                            var option = document.createElement("option");
                            option.text = respParsed[0].menu[i].name.toString().concat(" "+respParsed[0].menu[i].price);
                            option.value = respParsed[0].menu[i].name.toString().concat(" "+respParsed[0].menu[i].price);
                            elencoPiatti.append(option);
                        }
                        $('#ristorante').on('change', function (e) {
                            var optionSelected = $("option:selected", this);
                            var valueSelected = this.value;
                            for( i=0; i<respParsed.length; i++) {
                                if(valueSelected.toString()==respParsed[i].name.toString()){
                                    var pietanze=respParsed[i].menu;
                                }
                            };

                            var elencoPiatti =  document.getElementById('piatti');
                            elencoPiatti.innerText="";
                            for( i=0; i<pietanze.length; i++) {
                                var option = document.createElement("option");
                                option.text = pietanze[i].name.toString().concat(" "+pietanze[i].price);
                                option.value = pietanze[i].name;
                                elencoPiatti.append(option);
                            }
                        });

                        $('#first').hide();
                        $('#second').show();
                    }else{
                        $('#info').html("Siamo spiacenti, non ci sono ristoranti dosponibili nel comune selezionato. Riprova");
                    }

                } else {
                    console.log("xhr failed");
                }
            } else {
                //console.log("xhr processing going on");
            }
        }
        //console.log("request sent succesfully");

    }

    function confirmOrder() {

        var somethingEmpty=false;
        $('#info2').html("");
        $('#info3').html("");
        $('#info4').html("");

        console.log("Manda ordine");
        //TODO: raccogliere tutti i campi nel form 2 e formare un ordine in json

        //get selected restaurant
        var selectR = document.getElementById("ristorante");
        var choosenR = selectR.options[selectR.selectedIndex].value;

        //check and get selected dishes
        if ($('#piatti :selected').length > 0) {
            var opts = [], opt;
            var selectD = document.getElementById("piatti");
            for (var i = 0, len = selectD.options.length; i < len; i++) {
                opt = selectD.options[i];
                // check if selected
                if (opt.selected) {
                    var singleDish={};
                    singleDish.name=opt.value.split(" ")[0];
                    singleDish.price=opt.value.split(" ")[1];
                    opts.push(singleDish);
                }
            }
        }else{
            somethingEmpty=true;
            $('#info2').html("Seleziona almeno una portata dall'elenco");
        }


        //TODO: get selected time

        //check and get selected delivery address
        if (!document.getElementById("indirizzo").value){
            somethingEmpty=true;
            $('#info4').html("inserire indirizzo");
        }


        //TODO: get address of the selected restaurants

        if(!somethingEmpty) {
            var order =
                {
                    "restaurant": choosenR,
                    "delivery_time": "11",
                    "dishes": opts,
                    "from": "indirizzo del ristorante?", //TODO:save
                    "to": document.getElementById("indirizzo").value
                };

            console.log(order);
            var restaurant_url = "http://localhost:8080/acmeat-ws/send-order";
            var xhr = new XMLHttpRequest();
            xhr.withCredentials = true;
            xhr.open("POST", restaurant_url, true);
            xhr.setRequestHeader("Content-type", "application/json");
            var params = JSON.stringify(order);
            xhr.send(params);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        console.log("xhr done successfully");
                        var resp = xhr.responseText;
                        console.log(resp);
                        var respParsed = JSON.parse(resp);


                        //TODO: chiama interfaccia banca dove vi fate un altro bell'index.html
                        window.location=respParsed.bank_url + "?total_price="+ respParsed.total_price + "callback_url=" + window.location;
                        //console.log(respParsed);
                    } else {
                        //console.log("xhr failed with " + xhr.status);
                    }
                } else {
                    //console.log("xhr processing going on");
                }
            }
            //console.log("request sent succesfully");

            // commented for the moment
            $('#second').hide();
            $('#third').show();
        }



    }

    function pay(){
        $('#third').hide();
        $('#fourth').show();
    }

    function cancelOrder(){
        console.log("Cancellando ordine");

        var url="http://localhost:8060/acmeat/cancel";

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

</body>
</html>

