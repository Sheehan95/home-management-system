import web
import random

url = (
    '/', 'Index',
    '/temp', 'Temperature',
    '/motion', 'Motion'
)

class Index:
    def GET(self):
        return "Hello world!"


class Temperature:
    def GET(self):
        global temp
        print "TEMP:", temp
        return "Temperature: ", temp


class Motion:
    def GET(self):
        return "Motion"
    


if __name__ == "__main__":
    app = web.application(url, globals())
    app.run()


