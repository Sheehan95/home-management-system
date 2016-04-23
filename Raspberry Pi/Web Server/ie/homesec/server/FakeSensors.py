from threading import Thread
import random
from time import sleep


class TemperatureSensor:

    def __init__(self):
        self.temperature = 0
        self.thread = Thread(target=self.run)
        self.thread.start()

    def run(self):
        while True:
            self.temperature = random.random()
            sleep(5)


class MotionSensor:

    def __init__(self):
        self.coordinates = {'x': 0, 'y': 0, 'z': 0}
        self.thread = Thread(target=self.run)

    def run(self):
        while True:
            self.coordinates['x'] = random.random()
            self.coordinates['y'] = random.random()
            self.coordinates['z'] = random.random()
            sleep(5)
