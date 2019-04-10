# from flask import json
# from app import create_app
#
#
import json

from models.status import Status

place_order_content = {
                    "content": [
                                "pizza",
                                "carbonara"
                             ],
                    "delivery_time": "13"
               }

place_order_content_invalid = {
                    "content": [
                                "pinco",
                                "pallino"
                             ],
                    "delivery_time": "13"
               }

abort_order_content = {
                            "content": [
                                "pizza",
                                "carbonara"
                            ],
                            "delivery_time": "13",
                            "id": 1,
                            "status": "ACCEPTED"
                        }

#
#
# def test_place_order():
#     app = create_app('RestaurantServiceTest')
#     response = app.test_client().post(
#         '/restaurant/order',
#         data=post_content,
#         content_type='application/json',
#     )
#
#     data = json.loads(response.get_data())
#     test = response.get_data()
#     assert response.status_code == 200
#     assert data['id'] == 1


from app import app
import unittest


class TestRestaurantService(unittest.TestCase):
    def setUp(self):
        self.app = app.test_client()

    # def test_get_order(self):
    #     rv = self.app.get('/restaurant/order/1')
    #     self.assertEqual(rv.status, '200 OK')
    #     response = json.loads(rv.data.decode())
    #     self.assertIsNotNone(response["error"])
        # assert b'Main' in rv.data
        #assert False

    # def test_add(self):
    #     rv = self.app.post('/restaurant/order',
    #                        data=json.dumps(post_content),
    #                        content_type='application/json',
    #                        )
    #     self.assertEqual(rv.status, '200 OK')
    #     response = json.loads(rv.data.decode())
    #     self.assertIsNotNone(response["id"])
    #     self.assertEqual(1, response["id"])
    #     self.assertEqual(post_content["content"], response["content"])
    #     self.assertEqual(post_content["delivery_time"], response["delivery_time"])



    # def test_404(self):
    #     self.setUp()
    #
    #     response = self.app.get('/other')
    #     self.assertEqual(response.status, '404 NOT FOUND')


class PlaceOrderTest(TestRestaurantService):

    def test_with_valid_body(self):
        rv = self.app.post('/restaurant/order',
                           data=json.dumps(place_order_content),
                           content_type='application/json',
                           )
        self.assertEqual(rv.status, '200 OK')
        response = json.loads(rv.data.decode())
        self.assertIsNotNone(response["id"])
        self.assertEqual(place_order_content["content"], response["content"])
        self.assertEqual(place_order_content["delivery_time"], response["delivery_time"])
        rv = self.app.get('/restaurant/order/{}'.format(response["id"]))
        self.assertEqual(rv.status, '200 OK')
        response = json.loads(rv.data.decode())
        self.assertIsNone(response.get("error", None))
        self.assertEqual(place_order_content["content"], response["content"])
        self.assertEqual(place_order_content["delivery_time"], response["delivery_time"])

    def test_with_valid_body_and_invalid_content(self):
        rv = self.app.post('/restaurant/order',
                           data=json.dumps(place_order_content_invalid),
                           content_type='application/json',
                           )
        self.assertEqual(rv.status, '200 OK')
        response = json.loads(rv.data.decode())
        self.assertIsNotNone(response["id"])
        self.assertEqual(place_order_content_invalid["content"], response["content"])
        self.assertEqual(place_order_content_invalid["delivery_time"], response["delivery_time"])
        self.assertEqual(Status.NOT_ACCEPTED.name, response["status"])
        rv = self.app.get('/restaurant/order/{}'.format(response["id"]))
        self.assertEqual(rv.status, '200 OK')
        response = json.loads(rv.data.decode())
        self.assertIsNone(response.get("error", None))
        self.assertEqual(place_order_content_invalid["content"], response["content"])
        self.assertEqual(place_order_content_invalid["delivery_time"], response["delivery_time"])
        self.assertEqual(Status.NOT_ACCEPTED.name, response["status"])

    def test_with_invalid_body(self):
        rv = self.app.post('/restaurant/order',
                           content_type='application/json'
                           )
        self.assertEqual(rv.status, '400 BAD REQUEST')

class GetOrderTest(TestRestaurantService):

    def test_with_existing_order(self):
        self.app.post('/restaurant/order',
                       data=json.dumps(place_order_content),
                       content_type='application/json',
                        )
        rv = self.app.get('/restaurant/order/1')
        self.assertEqual(rv.status, '200 OK')
        response = json.loads(rv.data.decode())
        self.assertIsNone(response.get("error", None))
        self.assertEqual(place_order_content["content"], response["content"])
        self.assertEqual(place_order_content["delivery_time"], response["delivery_time"])

    def test_with_not_existing_order(self):
        rv = self.app.get('/restaurant/order/99')
        self.assertEqual(rv.status, '200 OK')
        response = json.loads(rv.data.decode())
        self.assertIsNotNone(response.get("error", None))
        self.assertEqual("Order 99 not found", response["error"])

class AbortOrderTest(TestRestaurantService):

    def test_with_aborted_order(self):
        self.app.post('/restaurant/order',
                       data=json.dumps(abort_order_content),
                       content_type='application/json',
                        )

        rv = self.app.put('/restaurant/order/abort',
                       data=json.dumps(abort_order_content),
                       content_type='application/json',
                        )

        response = json.loads(rv.data.decode())
        self.assertIsNotNone(response["id"])
        rv = self.app.get('/restaurant/order/{}'.format(response["id"]))
        self.assertEqual(rv.status, '200 OK')
        response = json.loads(rv.data.decode())
        self.assertIsNone(response.get("error", None))
        self.assertEqual(place_order_content["content"], response["content"])
        self.assertEqual(place_order_content["delivery_time"], response["delivery_time"])
        self.assertEqual(Status.ABORTED.name, response["status"])

    def test_with_not_aborted_order(self):
        rv = self.app.post('/restaurant/order',
                       data=json.dumps(place_order_content),
                       content_type='application/json',
                        )

        response = json.loads(rv.data.decode())
        self.assertIsNotNone(response["id"])
        rv = self.app.get('/restaurant/order/{}'.format(response["id"]))
        self.assertEqual(rv.status, '200 OK')
        response = json.loads(rv.data.decode())
        self.assertIsNone(response.get("error", None))
        self.assertEqual(place_order_content["content"], response["content"])
        self.assertEqual(place_order_content["delivery_time"], response["delivery_time"])
        self.assertEqual(Status.ACCEPTED.name, response["status"])