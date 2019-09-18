### [**🏠 Home**](/README.md)

###  [**⬅️ Back**](external-services.md)

-----
# ACMEat Agency (Italian only)

L'agenzia è stata implementata tramite BPMN eseguibile sul process engine messo a disposizione da camunda platform oltre che ad uno strato backend (acmeat-ws) e uno frontend dedicato a ristoranti e clienti.

L'intera agezia è un progetto Maven che comprende i sottoprogetti esposti di seguito.

## BPMS [(sources)](https://github.com/AdamF42/acmEat/tree/master/acme-agency/acmeat)

Per il BPMS è stato utilizzato Camunda che grazie al process engine è in grado di eseguire BPM scritti in BPMN 2.0.
All'interno del progetto sono presenti i package contenenti le  classi JavaDelegate con le implementaizoni delle logiche dei relativi task:

* [acme](https://github.com/AdamF42/acmEat/tree/master/acme-agency/acmeat/src/main/java/it/unibo/acme): contiene le classi relative ai task eseguiti da acme stessa
* [bank](https://github.com/AdamF42/acmEat/tree/master/acme-agency/acmeat/src/main/java/it/unibo/bank): contiene le classi che si occupano della comunicaizione con il servizio bancario
* [delivery](https://github.com/AdamF42/acmEat/tree/master/acme-agency/acmeat/src/main/java/it/unibo/delivery): contiene le classi relative all'interazione con le compagnie di delivery
* [restaurant](https://github.com/AdamF42/acmEat/tree/master/acme-agency/acmeat/src/main/java/it/unibo/restaurant): contiene le classi relative all'interazione con i ristoranti
* [utils](https://github.com/AdamF42/acmEat/tree/master/acme-agency/acmeat/src/main/java/it/unibo/utils): contiene due classi di utilità. In particolare la classe [AcmeErrorMessages](https://github.com/AdamF42/acmEat/blob/master/acme-agency/acmeat/src/main/java/it/unibo/utils/AcmeErrorMessages.java) raccoglie i messaggi di errore specificati nel BPM.

## WebServices [(sources)](https://github.com/AdamF42/acmEat/tree/master/acme-agency/acmeat-ws)

Il progetto acmeat-ws tramite CDI accede al process engine ed espone la seguente interfaccia al frontend:

* [GetRestaurants](https://github.com/AdamF42/acmEat/blob/master/acme-agency/acmeat-ws/src/main/java/it/unibo/GetRestaurants.java): Permette di ricevere la lista dei locali disponibili
* [SendOrder](https://github.com/AdamF42/acmEat/blob/master/acme-agency/acmeat-ws/src/main/java/it/unibo/SendOrder.java): permette di inviare l'ordine e di controllare la disponibilità del ristorante e dei servizi di delivery. Ritorna l'url della banca per effettuare il pagamento.
* [ConfirmOrder](https://github.com/AdamF42/acmEat/blob/master/acme-agency/acmeat-ws/src/main/java/it/unibo/ConfirmOrder.java): permette di verificare il token ritornato dalla banca e di inviare la conferma dell'ordine al serizio di delivery e al ristorante.
* [AbortOrder](https://github.com/AdamF42/acmEat/blob/master/acme-agency/acmeat-ws/src/main/java/it/unibo/AbortOrder.java): consente di annullare l'odine.
* [ChangeRestaurantAvailability](https://github.com/AdamF42/acmEat/blob/master/acme-agency/acmeat-ws/src/main/java/it/unibo/ChangeRestaurantAvailability.java): permette di modificare la disponibilità del ristorante che lo richiede.
* [ChangeRestaurantMenu](https://github.com/AdamF42/acmEat/blob/master/acme-agency/acmeat-ws/src/main/java/it/unibo/ChangeRestaurantMenu.java): permette di modificare il menu del ristorante che lo richiede.

### Frontend [(sources)](https://github.com/AdamF42/acmEat/tree/master/acme-agency/acmeat-frontend)

Il progetto frontend definisce le interfaccie web per utenti e ristoratori. Questo chiama le API esposte da acmeat-ws

#### Frontoffice 

#### Backoffice 

## Common [(sources)](https://github.com/AdamF42/acmEat/tree/master/acme-agency/common)

Il progetto common contiene tutte le classi comuni ai tre progetti, in particolare i modelli, il repository e le costanti utilizzate nei messaggi e nelle variabili dal process engine.

-----

