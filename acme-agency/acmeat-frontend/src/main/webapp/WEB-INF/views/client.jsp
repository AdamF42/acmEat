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
<div id="info"  style="color:blue"></div>
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
    <input type="time" id="deliveryTimeC" name="deliveryTimeC" required><br>
    <div id="info3"  style="color:red"></div>

    <br><br>

    Inserisci l'indirizzo di consegna:
    <input type="text" id="indirizzo" name="indirizzo" value="">
    <div id="info4"  style="color:red"></div>

    <br><br>

    <input type="submit" id="ordButton" onclick="confirmOrder()" value="Ordina">

</div>


<br><br>
<div id="third" hidden="true"> Stiamo elaborando il tuo ordine, se ci sono le disponibilita verrai reindirizzato alla banca per il pagamento.</div>
<br><br>

<div id="info5" style="color:red"></div>

<script type='text/JavaScript'>
    var d = new Date();
    var h = d.getHours();
    var m = d.getMinutes();

    document.getElementById("deliveryTimeC").setAttribute("min",h.toString().concat(":"+m.toString()));

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
                    console.log(JSON.parse(resp).result.status);
                    if(JSON.parse(resp).result.status.toString()=="success"){
                        var respParsed = JSON.parse(resp).restaurants;
                        $('#info').html("");
                        var elenco =  document.getElementById('ristorante');
                        for( i=0; i<respParsed.length; i++) {
                            var option = document.createElement("option");
                            option.text = respParsed[i].name;
                            option.value = respParsed[i].name.toString().concat(";"+respParsed[i].city);
                            elenco.append(option);
                        }
                        var elencoPiatti =  document.getElementById('piatti');
                        elencoPiatti.innerText="";
                        for( i=0; i<respParsed[0].menu.length; i++) {
                            var option = document.createElement("option");
                            option.text = respParsed[0].menu[i].name.toString().concat(" "+respParsed[0].menu[i].price);
                            option.value = respParsed[0].menu[i].name.toString().concat(";"+respParsed[0].menu[i].price);
                            elencoPiatti.append(option);
                        }
                        $('#ristorante').on('change', function (e) {
                            var optionSelected = $("option:selected", this);
                            var valueSelected = this.value;
                            var pietenze;
                            for( i=0; i<respParsed.length; i++) {
                                if(valueSelected.toString().split(";")[0]==respParsed[i].name.toString()){
                                    pietanze=respParsed[i].menu;
                                }
                            };

                            var elencoPiatti =  document.getElementById('piatti');
                            elencoPiatti.innerText="";
                            for( i=0; i<pietanze.length; i++) {
                                var option = document.createElement("option");
                                option.text = pietanze[i].name.toString().concat(" "+pietanze[i].price);
                                option.value = pietanze[i].name.toString().concat(";"+pietanze[i].price);
                                elencoPiatti.append(option);
                            }
                        });
                        $('#first').hide();
                        $('#second').show();
                    }
                    else {
                        $('#info').html(JSON.parse(resp).result.message);
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


        //get selected restaurant
        var selectR = document.getElementById("ristorante");
        var choosenRname = selectR.options[selectR.selectedIndex].value.split(";")[0];
        var choosenRaddress = selectR.options[selectR.selectedIndex].value.split(";")[1];


        //check and get selected dishes
        if ($('#piatti :selected').length > 0) {
            var opts = [], opt;
            var selectD = document.getElementById("piatti");
            for (var i = 0, len = selectD.options.length; i < len; i++) {
                opt = selectD.options[i];
                // check if selected
                if (opt.selected) {
                    var singleDish={};
                    singleDish.name=opt.value.split(";")[0];
                    singleDish.price=opt.value.split(";")[1];
                    opts.push(singleDish);
                }
            }
        }else{
            somethingEmpty=true;
            $('#info2').html("Seleziona almeno una portata dall'elenco");
        }

        var deliveryTimeField=document.getElementById('deliveryTimeC');
        if(!deliveryTimeField.checkValidity()){
            somethingEmpty=true;
            $('#info3').html(deliveryTimeField.validationMessage);
        }else{
            var deliveryTime=document.getElementById("deliveryTimeC").value;
        }

        //check and get selected delivery address
        if (!document.getElementById("indirizzo").value){
            somethingEmpty=true;
            $('#info4').html("Inserisci l'indirizzo di consegna");
        }


        if(!somethingEmpty) {

            $('#second').hide();
            $('#third').show();

            var order =
                {
                    "restaurant": choosenRname,
                    "delivery_time": deliveryTime,
                    "dishes": opts,
                    "from": choosenRaddress,
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
                        var respParsed = JSON.parse(resp);
                        $('#third').hide();
                        if(respParsed.result.status.toString()=="success"){
                            var callback_url=encodeURIComponent("http://localhost:8080/acmeat-frontend/client-after-payment");
                            var bank_url="http://localhost:8070/bank/home/"+"price/"+ respParsed.total_price + "/callback_url/" + callback_url;
                            window.location.assign(bank_url);
                        }else{
                            //TODO: ridargli la pox di formulare un ordine? partendo dall'inizio con $('#first').show();
                            $('#info5').html(JSON.parse(resp).result.message);
                        }

                    } else {
                        //console.log("xhr failed with " + xhr.status);
                    }
                } else {
                    //console.log("xhr processing going on");
                }
            }
            //console.log("request sent succesfully");
        }
    }



</script>

</body>
</html>

