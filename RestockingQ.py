"""
Name: Yi Jiaxin
Student Number: 233517S
Tutorial Group: 6, IT2306
"""


class RestockingQ:
    # Creates an empty Queue.
    def __init__(self, max_size):
        self._count = 0
        self._front = 0
        self._back = max_size - 1
        self._qArray = [None] * max_size

    # Returns True if the queue is empty.
    def isEmpty(self):
        return self._count == 0

    # Returns True if the queue is full.
    def isFull(self):
        return self._count == len(self._qArray)

    # Returns the number of items in the queue.
    def __len__(self):
        return self._count

    def items(self):
        return self._qArray

    def start(self):
        return self._front

    # Adds the given item to the queue.
    def enqueue(self, item):
        assert not self.isFull(), "Cannot enqueue to a full queue."
        max_size = len(self._qArray)
        self._back = (self._back + 1) % max_size
        self._qArray[self._back] = item
        self._count += 1

    # Removes and returns the first item in the queue.
    def dequeue(self):
        assert not self.isEmpty(), "Cannot dequeue from an empty queue."
        item = self._qArray[self._front]
        max_size = len(self._qArray)
        self._front = (self._front + 1) % max_size
        self._count -= 1
        return item

    # Return the content of the queue (with array index in square # brackets).
    def __str__(self):
        max_size = len(self._qArray)
        out_str = ''
        for i in range(self._count):
            out_str += ('[' + str((self._front + i) % max_size) + ']:')
            out_str += (str(self._qArray[(self._front + i) % max_size]) + ' ')
        return out_str
