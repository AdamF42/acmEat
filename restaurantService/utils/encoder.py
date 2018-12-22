from flask.json import JSONEncoder
from models.order import Order


class MyJSONEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Order):
            return {
                'id': obj.order_id,
                'content': obj.content,
                'delivery_time': obj.delivery_time,
                'status': obj.status
            }
        return super(MyJSONEncoder, self).default(obj)