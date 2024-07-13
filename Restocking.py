# Option 7 - Go to Restocking Menu
def restocking_menu():
    while True:
        print("Restocking Menu:")
        print("1. Enter new stock arrival\n"
              "2. View number of stock arrival\n"
              "3. Service next restock in queue"
              "0. Return to Main Menu")

        try:
            choice = input("Enter your choice: ")
            match choice:
                case "1":
                    return
                case "2":
                    return
                case "3":
                    return
                case "0":
                    return
                case _:
                    print("Your choice isn't in the list. Please try again.")

        except ValueError:
            print("Your choice is not an integer. Please try again.")
            restocking_menu()
        except Exception as e:
            raise e


