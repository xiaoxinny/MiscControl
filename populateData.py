"""
Name: Yi Jiaxin
Student Number: 233517S
Tutorial Group: 6, IT2306
"""

from Stationary import *
import datetime

# Global parameters
prodList = []


# Option 1 - Add new stationary
# Adding stationary with validity verification and auto-restart
def add_new_stationary():
    try:
        prod_id = None
        while prod_id is None:
            prod_id_input = input("Enter Product ID: ")
            if checker(prod_id_input, "id"):
                print("Product ID exists in the database. Please try again.")
            else:
                prod_id = prod_id_input

        prodname = None
        while prodname is None:
            prodname_input = input("Enter Product Name: ")
            if checker(prodname_input, "name"):
                while True:
                    option = input("This product name exists in the database. Are you sure you want to continue? [Y/N]")
                    if option == "Y" or option == "y":
                        prodname = prodname_input
                        break
                    elif option == "N" or option == "n":
                        print(f"Please try again. Your previous name input was {prodname_input}.")
                        break
                    else:
                        print("Your choice isn't in the list. Please try again.")
            else:
                prodname = prodname_input

        # No need for validation
        category = input("Enter Product Category: ")
        brand = input("Enter Brand:")

        supplier_since = None
        year_limit = datetime.datetime(1800, 1, 1)
        while supplier_since is None:
            try:
                supplier_input = int(input("Please enter the year this supplier started supplying this product: "))
                if supplier_input < int(year_limit.strftime("%Y")):
                    raise "The year given is not a valid year. Please try again."
                else:
                    supplier_since = supplier_input
            except Exception as e:
                print("Your input was not accepted. Please try again. Read below for your error.")
                raise e

        stock = input("Enter Stock: ")

        newStud = Stationary(prod_id, prodname, category, brand, supplier_since, stock)
        return newStud

    except TypeError:
        print("Your input is not of the right format. Please try again.")
        add_new_stationary()

    except ValueError:
        print("Your input has exceeded the accepted parameters. Please try again.")
        add_new_stationary()


# Option 9 - Populate data
# Basic data populating. Don't touch.
def populateData():
    newStudA = Stationary("PD1020", "Pastel Art Paper", "Paper", "Faber-Castell", 2021, 2000)
    prodList.append(newStudA)
    newStudA = Stationary("PD1025", "Mars Lumograph Drawing Pencils", "Pencils", "Staedtler", 2022, 320)
    prodList.append(newStudA)
    newStudA = Stationary("PD1015", "Water color Pencils", "Pencils", "Faber-Castell", 2011, 150)
    prodList.append(newStudA)
    newStudA = Stationary("PD1050", "Noris 320 fiber tip pen", "Pens", "Staedtler", 2021, 350)
    prodList.append(newStudA)
    newStudA = Stationary("PD1001", "Copier Paper (A4) 70GSM", "Paper", "PaperOne", 2021, 1500)
    prodList.append(newStudA)
    newStudA = Stationary("PD1033", "Scientific Calculator FX-97SG X", "Calculator", "Casio", 2022, 50)
    prodList.append(newStudA)
    newStudA = Stationary("PD1005", "POP Bazic File Separator Clear", "Office Supplies", "Popular", 2000, 500)
    prodList.append(newStudA)
    print("Data populated!\n")
    return prodList


# Miscellaneous. Don't touch.
# Supplementary to Option 1f
def checker(data, field):
    for item in prodList:
        if field == "id":
            return item.get_prod_id() == data
        elif field == "name":
            return item.get_prod_name() == data
