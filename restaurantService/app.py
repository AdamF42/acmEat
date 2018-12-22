import json

from models.menu import Menu
from models.order import Order
from flask import jsonify, request, abort
from models.status import Status
from repo.menu_repository import menu
from utils.app_creator import create_app

app = create_app('RestaurantService')

my_menu = Menu(menu)


# ======== Routing =========================================================== #
# -------- PlaceOrder _------------------------------------------------------- #
@app.route('/restaurant/order', methods=['POST'])
def place_order():
    if 'content' not in request.json or 'delivery_time' not in request.json:
        abort(400)
    order = json.dumps(request.json)
    order = json.loads(order, object_hook=Order.dict_to_obj)
    if my_menu.is_available(order.content):
        order.status = Status.ACCEPTED.name
        Order.save(order)
        return jsonify(order)
    else:
        order.status = Status.NOT_ACCEPTED.name
        return jsonify(Order.save(order))


# -------- GetAvailability --------------------------------------------------- #
@app.route('/restaurant/availability', methods=['PUT'])
def get_availability():
    order = json.dumps(request.json)
    order = json.loads(order, object_hook=Order.dict_to_obj)
    if my_menu.is_available(order.content):
        order.status = Status.AVAILABLE.name
        return jsonify(order)
    else:
        order.status = Status.NOT_AVAILABLE.name
        return jsonify(Order.save(order))


# -------- AbortOrder -------------------------------------------------------- #
@app.route('/restaurant/order/abort', methods=['PUT'])
def abort_order():
    if 'id' not in request.json:
        abort(400)
    order = json.dumps(request.json)
    order = json.loads(order, object_hook=Order.dict_to_obj)
    return jsonify(Order.set_abort_for_order(order.order_id))


# -------- GetOrder -----------------------------------------------------------#
@app.route('/restaurant/order/<id>', methods=['GET'])
def get_order(id):
    order = Order.get_order_by_id(id)
    if order is not None:
        return jsonify(order)
    else:
        return not_found("Order {} not found".format(id))


# @app.errorhandler(404)
def not_found(error):
    return jsonify({'error': str(error)})


# ======== Main ============================================================== #
if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
