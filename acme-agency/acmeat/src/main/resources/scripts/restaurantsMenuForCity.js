var restaurants = '{"restaurants":[{"name":"Ciccio","menu": [{"name":"Lasagne", "price":"5"}, {"name":"Pizza", "price":"3"}]}, {"name":"Yoma","menu": [{"name":"Ravioli", "price":"5"}, {"name":"Sushi", "price":"3"}]}]}';
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