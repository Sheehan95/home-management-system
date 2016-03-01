from threading import Thread
from time import sleep
import random

# python uses indentation instead of brackets to break up blocks of code


# this is a class definition - it's a fake temperature sensor. (object) means it extends the object class
class TemperatureSensor(object):

    # this is a constructor in python. self is like 'this' in java, it's how an object refers to itself
    def __init__(self):
        # defining an instance variable, temp
        self.temp = 0
        # creating a thread object which runs the 'self.run' function
        self.thread = Thread(target=self.run)
        # starts that thread
        self.thread.start()

    # def is how you define a function. self is the first parameter of every function in python
    def run(self):
        while True:
            # read the temperature every second until shut down - randomly generates a new number
            self.temp = random.random()
            # prints the temperature value in the console
            print 'TEMPERATURE: ', self.temp
            # sleeps for 1 second
            sleep(1)

    # getter function for the temperature value
    def get_temp(self):
        return self.temp


class MotionSensor(object):

    def __init__(self):
        # key-value pairs. coordinates['x'] refers to the value after x
        self.coordinates = {'x': 0, 'y': 0, 'z': 0}
        self.thread = Thread(target=self.run)
        self.thread.start()

    def run(self):
        while True:
            # read the motion every second until shut down
            self.coordinates['x'] = random.random()
            self.coordinates['y'] = random.random()
            self.coordinates['z'] = random.random()
            print 'COORDINATES: ', self.coordinates
            sleep(1)

    def get_coordinates(self):
        return self.coordinates
