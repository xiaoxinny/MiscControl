"""
Name: Yi Jiaxin
Student Number: 233517S
Tutorial Group: 6, IT2306
"""

from populateData import *


# Option 3 - Sort Stationary via Bubble sort on Category
# Note - This is ascending order
def category_bubble_sort():
    n = len(prodList)
    count = 0
    for i in range(n - 1, 0, -1):
        swapped = False
        for j in range(i):
            if prodList[j].get_category() < prodList[j + 1].get_category():
                prodList[j], prodList[j + 1] = prodList[j + 1], prodList[j]
                swapped = True
        if not swapped:
            break
        count += 1
        print(f"Pass: {count}")
        print("-" * 15)
        for item in prodList:
            print(item.get_category(), end="\n")
        print("-" * 15)


# Option 4 - Sort Stationary via Insertion sort on Brand
# Note - This is ascending order
def brand_insertion_sort():
    n = len(prodList)
    for i in range(1, n):
        value = prodList[i]
        current = i
        while current > 0 and value.get_brand() < prodList[current - 1].get_brand():
            prodList[current] = prodList[current - 1]
            current -= 1
        prodList[current] = value
        print(f"Pass: {i}")
        print("-" * 15)
        for item in prodList:
            print(item.get_prod_id(), end="\n")
        print("-" * 15)


# Option 5 - Sort Stationary via Selection sort on Prod ID
# Note - This is descending order
def prodId_selection_sort():
    n = len(prodList)
    for i in range(n - 1):
        large = i
        for j in range(i+1, n):
            if prodList[j].get_prod_id() > prodList[large].get_prod_id():
                large = j

        if large != i:
            tmp = prodList[large]
            prodList[large] = prodList[i]
            prodList[i] = tmp

        print(f"Pass: {i+1}")
        print("-" * 15)
        for item in prodList:
            print(item.get_prod_id(), end="\n")
        print("-" * 15)


# Option 6 - Sort Stationary via Merge sort on Category followed by Stock in descending order
def category_merge_sort():
    if len(prodList) < 2:
        return prodList

    else:
        # Compute the mid point
        mid = len(prodList) // 2

        # Split the list and perform recursive step
        left = category_merge_sort(prodList[:mid])
        right = category_merge_sort(prodList[mid:])

        # Merge the lists
        newList = mergeSortedLists(left, right)
        return newList


# Merge two sorted lists to create and return a new sorted list
# Supplementary to Option 6
def mergeSortedLists(listA, listB):
    newList = []
    a = 0
    b = 0

    # Merge the two lists together until one is empty
    while a < len(listA) and b < len(listB):
        if listA[a] < listB[b]:
            newList.append(listA[a])
            a += 1
        else:
            newList.append(listB[b])
            b += 1

    # If listA contains more items, append remaining items to newList
    while a < len(listA):
        newList.append(listA[a])
        a += 1

    # If listB contains more items, append remaining items to newList
    while b < len(listA):
        newList.append(listB[b])
        b += 1

    return newList