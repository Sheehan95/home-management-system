import web
import json
import datetime
import random


# list of urls being served by the server
urls = (
#   '{path},        '{ClassName}',
    '/',            'Index',
    '/Temperature', 'TemperatureHandler',
    '/Alarm',       'AlarmHandler'
)

# starts the web server
app = web.application(urls, globals())
alarm_armed = False


def my_processor(handler):
    print "Pre handling"
    handler()
    print "Post handling"


def my_loadhook():
    print "LOAD HOOK"


def my_unloadhook():
    print "UNLOAD HOOK"

app.add_processor(my_processor)
app.add_processor(web.loadhook(my_loadhook))
app.add_processor(web.unloadhook(my_unloadhook))


# class which acts as a handler
class Index:
    # initializes every time server gets request at '/'
    def __init__(self):
        print "Creating index..."

    # serves GET requests at '/'
    def GET(self):
        print 'GET: %s' % self.__class__
        return "Hello world!"

    # serves POST requests at '/'
    def POST(self):
        print web.data()
        print 'POST: %s' % self.__class__
        return "POST DONE"


# Handler Class
class TemperatureHandler:
    def __init__(self):
        print "Creating TemperatureHandler"

    def GET(self):
        print 'GET: %s' % self.__class__
        temperature = random.random()
        date = datetime.datetime.now()
        data = {'temp': temperature, 'date': date}  # place the data in key:value pairs
        print 'DATE:\t\t\t%s' % date
        print 'TEMPERATURE:\t%s' % temperature
        return json.dumps(data, cls=DateTimeEncoder)  # convert the key:value pairs into JSON

    def POST(self):
        print 'POST: %s' % self.__class__
        data = web.data()  # get the data sent to POST
        print data
        d = json.loads(data)  # parses the data as a JSON object
        print d['name']  # returns the value of 'name'
        return "POST DONE"


class AlarmHandler:
    def __init__(self):
        print "Creating AlarmHandler"

    def GET(self):
        print 'GET: %s' % self.__class__
        return 'Alarm Armed: %s' % str(alarm_armed)

    def POST(self):
        print 'POST: %s' % self.__class__
        
        data = json.loads(web.data())

        if 'arm' in data:
            global alarm_armed
            alarm_armed = data['arm']

        return "ALARM POST FINISHED"


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
