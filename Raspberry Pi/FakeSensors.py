import random


class TemperatureSensor:

    def __init__(self):
        self.temperature = 0

    def get_temp(self):
        self.temperature = random.random()
        return self.temperature


class MotionSensor:

    def __init__(self):
        self.cords = {'x': 0, 'y': 0, 'z': 0}

    def get_cords(self):
        self.cords['x'] = random.random
        self.cords['y'] = random.random
        self.cords['z'] = random.random
        return self.cords
