from time import sleep
from copy import deepcopy
from mpl3115a2 import mpl
from mma8491q import mma


class TemperatureSensor:

    def __init__(self):
        self.temperature = 0
        self.sensor = mpl()
        self.sensor.initAlt()
        self.sensor.active()

    def get_temp(self):
        self.temperature = self.sensor.getTemp()
        return self.temperature


class MotionSensor:

    def __init__(self):
        self.cords = {'x': 0, 'y': 0, 'z': 0}
        self.init_cords = {'x': 0, 'y': 0, 'z': 0}
        self.sensor = mma()
        self.sensor.init()
        self.sensor.enable()

        sleep(3)
        self.init_cords = deepcopy(self.get_cords())

    def get_cords(self):
        (x, y, z) = self.sensor.getAccelerometer()
        self.cords['x'] = x
        self.cords['y'] = y
        self.cords['z'] = z
        self.sensor.enable()
        return self.cords
