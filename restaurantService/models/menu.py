import random

class Menu:
    def __init__(self, menu):
        self.menu = menu

    def get_menu_items(self):
        return self.menu

    def is_available(self,content):
        return bool(random.getrandbits(1))
