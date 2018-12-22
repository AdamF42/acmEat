class Menu:
    def __init__(self, menu):
        self.menu = menu

    def get_menu_items(self):
        return self.menu

    def is_available(self,content):
        for item in content:
            if item not in self.get_menu_items():
                return False
        return True