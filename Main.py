"""
Name: Yi Jiaxin
Student Number: 233517S
Tutorial Group: 6, IT2306
"""

from Sorting import *
from populateData import *
from Restocking import *

# Global parameters
items_per_row = 1
limit = 40 - items_per_row * 5


# Main menu when program is initiated
def main():
    while True:
        print("Stationary Management System")
        print("1. Add a new Stationary\n"
              "2. Display all Stationary\n"
              "3. Sort Stationary via Bubble sort on Category\n"
              "4. Sort Stationary via Insertion sort on Brand\n"
              "5. Sort Stationary via Selection sort on Prod ID\n"
              "6. Sort Stationary via Merge sort on Category followed by Stock in descending order\n"
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
                    prodId_selection_sort()
                case 6:
                    category_merge_sort()
                case 7:
                    restocking_menu()
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
            main()
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
    if counter + items_per_row < length:
        for item in prodList[counter: counter + items_per_row]:         # Print IDs
            data = item.return_data()
            length_checker(data[0])
        print("\n")
        for item in prodList[counter: counter + items_per_row]:         # Print name
            data = item.return_data()
            length_checker(data[1])
        print("\n")
        for item in prodList[counter: counter + items_per_row]:         # Print category
            data = item.return_data()
            length_checker(data[2])
        print("\n")
        for item in prodList[counter: counter + items_per_row]:         # Print brand
            data = item.return_data()
            length_checker(data[3])
        print("\n")
        for item in prodList[counter: counter + items_per_row]:         # Print supplier since
            data = item.return_data()
            length_checker(data[4])
        print("\n")
        for item in prodList[counter:counter + items_per_row]:          # Print stock
            data = item.return_data()
            length_checker(data[5])
        print("\n")
        print("*" * limit * items_per_row)
        counter += items_per_row
        return rec_output_printer(counter)
    else:
        remaining = counter + (length - counter)
        for item in prodList[counter:remaining]:            # Print IDs
            data = item.return_data()
            length_checker(data[0])
        print("\n")
        for item in prodList[counter:remaining]:            # Print name
            data = item.return_data()
            length_checker(data[1])
        print("\n")
        for item in prodList[counter:remaining]:            # Print category
            data = item.return_data()
            length_checker(data[2])
        print("\n")
        for item in prodList[counter:remaining]:            # Print brand
            data = item.return_data()
            length_checker(data[3])
        print("\n")
        for item in prodList[counter:remaining]:            # Print supplier since
            data = item.return_data()
            length_checker(data[4])
        print("\n")
        for item in prodList[counter:remaining]:            # Print stock
            data = item.return_data()
            length_checker(data[5])
        print("\n")
        print("*" * limit * items_per_row)


main()
