"""
Name: Yi Jiaxin
Student Number: 233517S
Tutorial Group: 6, IT2306
"""


class Stationary:

    def __init__(self, Prod_id, ProdName, Category, Brand, Supplier_since, Stock):
        self.prod_id = Prod_id
        self.prodname = ProdName
        self.category = Category
        self.brand = Brand
        self.supplier_since = Supplier_since
        self.stock = Stock

    # Accessor

    def get_prod_id(self):
        return self.prod_id

    def get_prodname(self):
        return self.prodname

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

    def set_prodname(self, new_name):
        self.prodname = new_name

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
                f"Product Name: {self.prodname}\n"
                f"Product Category: {self.category}\n"
                f"Brand: {self.brand}\n"
                f"Supplier Year: {self.supplier_since}\n"
                f"Stock: {self.stock}")

    def return_data(self):
        return [f"Product ID: {self.prod_id}",
                f"Product Name: {self.prodname}",
                f"Product Category: {self.category}",
                f"Brand: {self.brand}",
                f"Supplier Year: {self.supplier_since}",
                f"Stock: {self.stock}"]
