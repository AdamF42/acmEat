var restaurants = '{"restaurants":[{"name":"Ciccio","menu":["Lasagne","Pizza"]},{"name":"Yoma","menu":["Ravioli","Sushi"]}]}';
var restaurantsNotFound = '{"restaurants":[]}';
var val = execution.getVariable("city");

switch (val) {
    case "Bologna":
        execution.setVariable("restaurants", restaurants);
        break;
    case 'Napoli':
        execution.setVariable("restaurants", restaurants);
        break;
    default:
        execution.setVariable("restaurants", restaurantsNotFound);
}