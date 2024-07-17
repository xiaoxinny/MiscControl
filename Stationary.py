"""
Name: Yi Jiaxin
Student Number: 233517S
Tutorial Group: 6, IT2306
"""


class Stationary:

    def __init__(self, prod_id, prod_name, category, brand, supplier_since, stock):
        self.prod_id = prod_id
        self.prod_name = prod_name
        self.category = category
        self.brand = brand
        self.supplier_since = supplier_since
        self.stock = stock

    # Accessor

    def get_prod_id(self):
        return self.prod_id

    def get_prod_name(self):
        return self.prod_name

    def get_category(self):
        return self.category

    def get_brand(self):
        return self.brand

    def get_supplier_since(self):
        return self.supplier_since

    def get_stock(self):
        return self.stock

    # Mutator

    def set_prod_id(self, new_id):
        self.prod_id = new_id

    def set_prod_name(self, new_name):
        self.prod_name = new_name

    def set_category(self, new_cat):
        self.category = new_cat

    def set_brand(self, new_brand):
        self.brand = new_brand

    def set_supplier_since(self, new_sup):
        self.supplier_since = new_sup

    def set_stock(self, new_stock):
        self.stock = new_stock

    def __str__(self):
        return (f"Product ID: {self.prod_id}\n"
                f"Product Name: {self.prod_name}\n"
                f"Product Category: {self.category}\n"
                f"Brand: {self.brand}\n"
                f"Supplier Year: {self.supplier_since}\n"
                f"Stock Remaining: {self.stock}")

    def return_data(self):
        return [f"Product ID: {self.prod_id}",
                f"Product Name: {self.prod_name}",
                f"Product Category: {self.category}",
                f"Brand: {self.brand}",
                f"Supplier Year: {self.supplier_since}",
                f"Stock Remaining: {self.stock}"]
