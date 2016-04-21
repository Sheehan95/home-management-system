import web
import json
import datetime
import random

# list of urls being served by the server
urls = (
    '/', 'Index',
    '/temperature', 'TemperatureHandler',
    '/alarm', 'AlarmHandler'
    # '/path', 'ClassHandlerName'
)

# starts the web server
app = web.application(urls, globals())


# class which acts as a handler
class Index:
    # initializes every time server gets request at '/'
    def __init__(self):
        print "Creating index..."

    # serves GET requests at '/'
    def GET(self):
        return "Hello world!"

    # serves POST requests at '/'
    def POST(self):
        print web.data()
        return "POST DONE"


# Handler Class
class TemperatureHandler:
    def __init__(self):
        print "Creating TemperatureHandler"

    def GET(self):
        temperature = random.random()
        date = datetime.datetime.now()
        data = {'temp': temperature, 'date': date}  # place the data in key:value pairs
        print 'DATE:\t\t\t%s' % date
        print 'TEMPERATURE:\t%s' % temperature
        return json.dumps(data, cls=DateTimeEncoder)  # convert the key:value pairs into JSON

    def POST(self):
        data = web.data()  # get the data sent to POST
        print data
        d = json.loads(data)  # parses the data as a JSON object
        print d['name']  # returns the value of 'name'
        return "POST DONE"


# Custom JSON encoder for dealing with datetime objects
class DateTimeEncoder(json.JSONEncoder):
    # overridden default method for parsing
    def default(self, obj):
        if isinstance(obj, datetime.datetime):  # if it's a datetime, parse as follows
            return {
                '__type__': 'datetime',
                'year': obj.year,
                'month': obj.month,
                'day': obj.day,
                'hour': obj.hour,
                'minute': obj.minute,
                'second': obj.second,
                'microsecond': obj.microsecond
            }
        else:
            return json.JSONEncoder.default(self, obj)  # otherwise parse as normal

# equivalent to public static void main
if __name__ == "__main__":
    app.run()
