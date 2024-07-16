class RestockDetail:
    def __init__(self, prod_id, quantity):
        self.prod_id = prod_id
        self.quantity = quantity

    # Accessor
    def get_prod_id(self):
        return self.prod_id

    def get_quantity(self):
        return self.quantity

    # Mutators
    def set_prod_id(self, prod_id):
        self.prod_id = prod_id

    def set_quantity(self, quantity):
        self.quantity = quantity
