import mpl3115a2 as temp
import time
import web

# URLs being served
urls = (
    '/', 'index'
    )

# initializing sensor for readings
sensor = temp.mpl()
sensor.initAlt()
sensor.active()

# giving the sensor time to start
time.sleep(1)


class index:
    
    def GET(self):
        # adding the temperature to the header
        web.header('temp', sensor.getTemp())
        return "The Temperature is: ", sensor.getTemp()


if __name__ == "__main__":
    app = web.application(urls, globals())
    app.run()
