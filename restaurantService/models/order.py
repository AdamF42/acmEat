from models.status import Status

orders = {}


class Order:

    @staticmethod
    def save(order):
        global orders
        order['id'] = len(orders) + 1
        orders[len(orders) + 1] = order
        return order

    @staticmethod
    def set_abort_for_order(id):
        order_to_abort = orders.get(int(id), None)
        if order_to_abort is not None:
            order_to_abort['status'] = Status.ABORTED.name
        return order_to_abort

    @staticmethod
    def get_order_by_id(id):
        default = None
        return orders.get(int(id), default)