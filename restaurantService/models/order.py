from models.status import Status

orders = {}


class Order:
    def __init__(self, content , delivery_time, order_id=None, status=None):
        self.order_id = order_id
        self.delivery_time = delivery_time
        self.status = status
        self.content = content

    @staticmethod
    def save(order):
        global orders
        order.order_id = len(orders) + 1
        orders[len(orders) + 1] = order
        return order

    @staticmethod
    def dict_to_obj(our_dict):
        default = None
        return Order(our_dict.get("content", default),
                     our_dict.get("delivery_time", default),
                     our_dict.get("id", default),
                     our_dict.get("status", default))

    @staticmethod
    def set_abort_for_order(id):
        default = None
        order_to_be_setted = orders.get(id, default)
        if orders.get(id, default) is not None:
            orders[id].status = Status.ABORTED.name
        return order_to_be_setted

    @staticmethod
    def get_order_by_id(id):
        default = None
        return orders.get(int(id), default)