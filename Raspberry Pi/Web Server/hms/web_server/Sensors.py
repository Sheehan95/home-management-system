from threading import Thread
from time import sleep
import random

class TemperatureSensor(object):

    def __init__(self):
        self.temp = 0
        self.thread = Thread(target=self.run)
        self.thread.start()

    def run(self):
        while True:
            # read the temperature every second until shut down
            self.temp = random.random()
            print 'TEMPERATURE: ', self.temp
            sleep(1)

    def get_temp(self):
        return self.temp


class MotionSensor(object):

    def __init__(self):
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
