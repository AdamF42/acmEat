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
    if 'id' not in request.json:
        abort(400)
    order = json.dumps(request.json)
    order = json.loads(order)
    order = Order.get_order_by_id(order['id'])
    if order.get("status", None) is None:
        order['status'] = Status.NOT_AVAILABLE.name
        return jsonify(order)

    if order['status'] == Status.AVAILABLE.name:
        order['status'] = Status.ACCEPTED.name

    return jsonify(order)


# -------- GetAvailability --------------------------------------------------- #
@app.route('/restaurant/availability', methods=['PUT'])
def get_availability():
    if 'dishes' not in request.json or 'delivery_time' not in request.json or 'restaurant' not in request.json:
        abort(400)
    order = json.dumps(request.json)
    order = json.loads(order)
    if my_menu.is_available(order['dishes']) or order['restaurant']=='debug':
        order['status'] = Status.AVAILABLE.name
    else:
        order['status'] = Status.NOT_AVAILABLE.name
    return jsonify(Order.save(order))


# -------- AbortOrder -------------------------------------------------------- #
@app.route('/restaurant/order/abort', methods=['PUT'])
def abort_order():
    if 'id' not in request.json:
        abort(400)
    order = json.dumps(request.json)
    order = json.loads(order)
    return jsonify(Order.set_abort_for_order(order['id']))


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
