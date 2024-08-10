"""
Name: Yi Jiaxin
Student Number: 233517S
Tutorial Group: 6, IT2306
"""

from populateData import *
from RestockDetail import *
from RestockingQ import *

# Initialize the queue to prevent redeclaration
queue = RestockingQ(10)


# Option 7 - Go to Restocking Menu
def restocking_menu():
    while True:
        print("Restocking Menu:")
        print("1. Enter new stock arrival\n"
              "2. View number of stock arrival\n"
              "3. Service next restock in queue\n"
              "0. Return to Main Menu")

        try:
            choice = input("Enter your choice: ")
            match choice:
                case "1":
                    new_stock()
                case "2":
                    view_stock_queue()
                case "3":
                    service_restock()
                case "0":
                    from Main import main_menu
                    main_menu()
                    break
                case _:
                    print("Your choice isn't in the list. Please try again.")

        except ValueError:
            print("Your choice is not an integer. Please try again.")
            restocking_menu()
        except Exception as e:
            raise e


# Submenu Option 1 - Enter new stock arrival
def new_stock():
    prod_id = None
    status = False
    while prod_id is None:
        prod_input = input("Enter Product ID: ")
        for item in prodList:
            if item.get_prod_id() == prod_input:
                prod_id = prod_input
                status = True
                break

        if not status:
            print("Invalid Product ID. Please try again.")
        else:
            break

    qty = None
    while qty is None:
        qty_input = input("Enter quantity to restock: ")
        try:
            converted = int(qty_input)
            qty = converted
            restock_item = RestockDetail(prod_id, qty)
            queue.enqueue(restock_item)
            print("Restocking arrival queued successfully!")
        except ValueError:
            print("Invalid quantity. Please try again.")


# Submenu Option 2 - View number of stock arrival
def view_stock_queue():
    print("Number of restocking in queue: ", queue.__len__())


# Submenu Option 3 - Service next restock in queue
def service_restock():
    items = queue.items()
    item = items[queue.start()]
    current_product = None
    for product in prodList:
        if item.get_prod_id() == product.get_prod_id():
            print("-" * 25)
            print(product.__str__())
            print("-" * 25)
            print("New Stock: ", item.get_quantity())
            print("-" * 25)
            current_product = product
    print("Remaining stock in queue: ", queue.__len__() - 1)

    option_input = input("Proceed with restock? (Y/N): ")
    while True:
        if option_input == "Y":
            updated_stock = item.get_quantity() + current_product.get_stock()
            print(f"Product ID: {item.get_prod_id()} | Updated Stock: {updated_stock}")
            current_product.set_stock(updated_stock)
            queue.dequeue()
            break
        elif option_input == "N":
            current = queue.dequeue()
            queue.enqueue(current)
            print(f"Product {current.get_prod_id()} re-queued!")
            break
        else:
            print("Invalid option. Please try again.")
