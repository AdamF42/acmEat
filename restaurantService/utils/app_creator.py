from flask import Flask
from utils.encoder import MyJSONEncoder


def create_app(config_name):
    app = Flask(__name__)
    app.json_encoder = MyJSONEncoder
    # app.config.from_object(config[config_name])
    # config[config_name].init_app(app)
    # bootstrap.init_app(app)
    # mail.init_app(app)
    # moment.init_app(app)
    # db.init_app(app)
    # #
    return app