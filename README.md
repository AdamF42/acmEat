
# ACMEat
[![BCH compliance](https://bettercodehub.com/edge/badge/AdamF42/acmEat?branch=master&token=bc829a4d4c6eb3d65cc8ab6dc721de1f05fc2fff)](https://bettercodehub.com/)
 
Service Oriented Software Engineering Project 18/19 University of Bologna

## Requirements (Italian only)

### Descrizione del dominio e del problema

La società ACMEat propone ai propri clienti un servizio che permette di selezionare un menu da uno fra un insieme di locali convenzionati e farselo recapitare a domicilio.


Per poter usufruire del servizio il cliente deve inizialmente selezionare un comune fra quelli nei quali il servizio è attivo. A fronte di questa selezione ACMEat presenta la lista dei locali convenzionati che operano in quel comune e dei menu che offrono. Il cliente può quindi specificare locale e menu di suo interesse e una fascia oraria per la consegna (si tratta di fasce di 15 minuti tra le 12 e le 14 e tra le 19 e le 21).

Segue quindi una fase di pagamento che viene gestita attraverso un istituto bancario terzo al quale il cliente viene indirizzato. A fronte del pagamento l’istituto rilascia un token al cliente il quale lo comunica ad ACMEat, che a sua volta lo usa per verificare con la banca che il pagamento sia stato effettivamente completato. A questo punto l’ordine diventa operativo. I clienti possono comunque ancora annullare l’ordine ma non più tardi di un’ora prima rispetto all’orario di consegna. In tal caso ACMEat chiede alla banca l’annullamento del pagamento.


ACMEat conosce tutti i locali convenzionati nei vari comuni nei quali opera e i loro giorni e orari di operatività. Nel caso in cui un locale non sia disponibile in un giorno in cui dovrebbe normalmente essere aperto è responsabilità del locale stesso contattare ACMEat entro le 10 del mattino comunicando tale indisponibilità. Entro tale orario vanno anche comunicati cambiamenti dei menu proposti (in mancanza di tale comunicazione si assume che siano disponibili gli stessi del giorno precedente). I locali vengono anche contattati ad ogni ordine per verificare che siano effettivamente in grado di far fronte alla richiesta del cliente. In caso negativo l’accettazione dell’ordine si interrompe prima che si passi alla fase di pagamento.


Per la consegna ACMEat si appoggia a più società esterne: per ogni consegna vengono contattate tutte le società che abbiano sede entro 10 chilometri dal comune interessato specificando: indirizzo del locale dove ritirare il pasto, indirizzo del cliente cui recapitarlo e orario previsto di consegna. A fronte di questa richiesta le società devono rispondere entro 15 secondi specificando la loro disponibilità e il prezzo richiesto; ACMEat sceglierà fra le disponibili che avranno risposto nel tempo richiesto quella che propone il prezzo più basso. Nel caso in cui nessuna società di consegna sia disponibile l’ordine viene annullato prima che si passi alla fase di pagamento.

### Workflow e artefatti

Si modellino le comunicazioni dello scenario sopra esposto usando una coreografia, si discutano le sue proprietà di connectedness ed eventualmente si raffini la coreografia per migliorare tali proprietà. Si proietti la coreografia in un sistema di ruoli.


Utilizzando uno o più diagrammi di collaborazione BPMN si modelli l’intera realtà descritta compresi i dettagli di ogni partecipante (usando il processo del ruolo corrispondente come guida). Tale modellazione ha scopo documentativo quindi il livello di dettaglio deve essere consistente con tale scopo.


Si progetti una SOA per la realizzazione del sistema e la si documenti utilizzando UML (eventualmente con opportuni profili, ad esempio TinySOA).


Si realizzi il sistema usando come tecnologie un BPMS (si consiglia di utilizzare Camunda), Jolie e API Rest, coi seguenti vincoli:

* Il servizio ACMEat, almeno per la parte di human workflow, deve rendere accessibili capabilities realizzate attraverso il BPMS;
* servizi esterni ad ACMEat devono essere (almeno): GIS, servizi bancari, locali e società di consegna;
* servizi di cui sopra vanno implementati (con logica elementare) come parte del progetto;
* servizi bancari devono essere realizzati in Jolie;
* almeno un servizio va realizzato con API Rest.

I modelli di processo BPMN da utilizzare per il BPMS devono essere consistenti con la modellazione a scopo documentativo precedentemente realizzata; volendo si può anche scegliere di dettagliare compiutamente già dal primo modello le pool eseguibili. Quindi nel primo caso si avrebbe un primo modello BPMN documentativo e poi tanti modelli BPMN eseguibili quanti i partecipanti realizzati attraverso BPMS; in alternativa si avrebbe un unico modello BPMN con le pool eseguibili completamente dettagliate e gli altri partecipanti dettagliati a livello documentativo.


Il dialogo fra Jolie e BPMS deve avvenire via SOAP, si veda il sito del corso alla pagina delle risorse per informazioni ulteriori.


### Opzioni
Fermo restando che la corretta realizzazione del progetto proposto senza la parte opzionale permette di ottenere comunque il massimo punteggio, viene proposta una consegna aggiuntiva da considerarsi opzionale:

1. Modellazione della coreografia anche attraverso un diagramma di coreografia BPMN.



<!-- ## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo -->


## Procedures (GitFlow)

1. Create a branch from master

>The name of the branch should be reasonable. Choose a name which explain the feature or the bug you are working for.
>All names MUST start with bug/ or feature/

2. Start working

3. Fetch update from master

4. Rebase master onto current branch

5. Check your mods

6. Create a pull request


<!-- ## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags).

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc -->
