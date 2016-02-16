import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

pwr1 = 1
pwr2 = 17

pwr3 = 22

GPIO.setup(pwr1, GPIO.OUT)
GPIO.setup(pwr2, GPIO.OUT)
GPIO.setup(pwr3, GPIO.OUT)

GPIO.output(pwr3, 1)

mpl = 13
GPIO.setup(mpl, GPIO.IN)

while True:
    print "Input: %d", GPIO.input(mpl)
    time.sleep(.5)
