"""
Name: Yi Jiaxin
Student Number: 233517S
Tutorial Group: 6, IT2306
"""

from Sorting import *
from populateData import *

# Global parameters
items_per_row = 1
limit = 40 - items_per_row * 5


# Menu when program is initiated
def main_menu():
    while True:
        print("Stationary Management System")
        print("1. Add a new Stationary\n"
              "2. Display all Stationary\n"
              "3. Sort Stationary via Bubble sort on Category\n"
              "4. Sort Stationary via Insertion sort on Brand\n"
              "5. Sort Stationary via Selection sort on Prod ID\n"
              "6. Sort Stationary via Merge sort on Category followed by Stock in ascending order\n"
              "7. Go to Restocking Menu\n"
              "8. Set number of records per row to display\n"
              "9. Populate Data\n"
              "0. Exit Program")
        try:
            choice = int(input("Please select one:"))
            match choice:
                case 1:
                    prodList.append(add_new_stationary())
                case 2:
                    display_all_stationary()
                case 3:
                    category_bubble_sort()
                case 4:
                    brand_insertion_sort()
                case 5:
                    prod_id_selection_sort()
                case 6:
                    category_merge_sort(prodList)
                case 7:
                    from Restocking import restocking_menu
                    restocking_menu()
                    break
                case 8:
                    row_display_count()
                case 9:
                    populateData()
                case 0:
                    print("Good bye!")
                    break
                case _:
                    print("Your choice isn't in the list. Please try again.")
        except ValueError:
            print("Your choice is not an integer. Please try again.")
            main_menu()
        except Exception as e:
            raise e


# Option 2 - Display all stationary
def display_all_stationary():
    if len(prodList) == 0:
        print("No data to display. Please populate data first.")
    else:
        if items_per_row == 1:
            for i in range(len(prodList)):
                print("-"*15)
                print(f"{prodList[i].__str__()}")
            print("-"*15)
        else:
            print("*" * limit * items_per_row)
            rec_output_printer()


# Option 8 - Set number of records per row to display
def row_display_count():
    try:
        global items_per_row
        items_per_row = int(input("Please enter the number of records to display per row: "))
        return items_per_row
    except ValueError or TypeError:
        print("Please enter an integer. Try again.")
        row_display_count()


# Miscellaneous. Don't touch.
"""
This function automates the printing procedure.
It ensures that the length of the output is within certain limits.
"""
def length_checker(data):
    length = len(data)
    print(data+(" " * (limit-length)), end="")


"""
This function automates the printing procedure.
It ensures that the number of items printed per row conforms to the parameters.
Counter keeps track of the current printed items. 
If the next iteration of rows exceed the limit, it wraps around to a new one.
"""


def rec_output_printer(counter=0):
    length = len(prodList)
    if counter < length:
        end = min(counter + items_per_row, length)

        fields = [0, 1, 2, 3, 4, 5]  # Indices of the data fields to be printed
        for field in fields:
            for item in prodList[counter:end]:
                data = item.return_data()
                length_checker(data[field])
            print("\n")

        print("*" * limit * items_per_row)

        counter += items_per_row
        return rec_output_printer(counter)


main_menu()
