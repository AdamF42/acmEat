### [**🏠 Home**](/README.md)

###  [**⬅️ Back**](/bpmn.md)
-----
# External Services (Italian only)

E' possibile trovare ulteriori dettagli sui servizi seguendo i link  a github specificati in `sources`.

## Bank [(sources)](https://github.com/AdamF42/acmEat/tree/master/bankService)

Il servizio è stato implementato in Jolie utilizzando il protocollo SOAP. Inoltre è presente un'iterfaccia web realizzata in Java che espone la sola getToken.

Sono state implementate le operazioni di:
- `getToken`
- `verifyToken`
- `refound`


Per rendere il servizio più semplice possibile non sono state aggiunte le normali operazioni di login/logout.

Il servizio dopo dopo la chiamata alla getToken genera un token (`sid`) che viene utilizzato per le sucessive chiamate come parametro.

Utilizzando la funzione random messa a disposizione dal linguaggio viene deciso se il pagamento dell'utente è andato a buon fine o meno (utilizzando come nome utente `debug` è la risposta sarà sempre positiva).

A questo punto viene salvato il nome dell'utente associato al risultato dell'operazione.

Dunque è possibile chiamare la verifyToken o la refound passando come parametro il `sid`.

Per implementare la sessione sono stati usati i **correlation set** nativi di Jolie.

## GIS [(sources)](https://github.com/AdamF42/acmEat/tree/master/gisService)

Il servizio gis è stato realizzato come servizio REST in Node.js ed opera con indirizzi reali utilizzando le API di [Graphhopper](https://www.graphhopper.com/).

Il servizio prevede due parametri GET, l'indirizzo di partenza e quello di arrivo, e restituisce una risposta così formata con un HTTP status code 200:

```json
{
    "message": "Distance: From: Milano, To: Bologna",
    "distance": 214972
}
```

Nel caso ci fossero dei problemi con gli indirizzi, viene resituito un HTTP status code 400 con body vuoto.

## Restaurant [(sources)](https://github.com/AdamF42/acmEat/tree/master/restaurantService)

Il servizio è stato implemetato utilizzato Python e il framework Flask.

Espone le seguenti chiamate:
- `GetAvailability`
- `SendOrder`
- `AbortOrder`
- `GetOrder` (usato solo per debug)

Al momento della chiamata alla `GetAvailability`, il servizio crea la risorsa ordine associandovi un id e uno stato. 
In modo casuale il servizio decide se può far fronte all'ordine. In caso positivo ritornerà una response con status `AVAILABLE` altrimenti lo status sarà `NOT_AVAILABLE`.
Se il body della richiesta dovesse essere malformato o se dovessero mancare parametri verrà restituito un `400 Bad Request`.

Con la `SendOrder` è possibile rendere l'odine effettivo. Alla chiamata il servizio controlla se lo stato dell'odine con l'id specificato è `AVAILABLE` . In caso affermativo lo setta ad `ACCEPTED`, altrimenti ritorna la risorsa con lo stato attuale (`NOT_ACCEPTED`).

Con l'`AbortOrder` viene modificato lo stato della risorsa con l'id specificato ad `ABORTED`.


## Delivery [(sources)](https://github.com/AdamF42/acmEat/tree/master/deliveryService)

------
<!-- ### [**➡️ Next**](acmeat-agency.md) -->

