import web
import json
import datetime
import random

urls = (
    '/',            'IndexHandler',
    '/Temperature', 'TemperatureHandler',
    '/Alarm',       'AlarmHandler'
)


# a processor hook that adds a list of variables to each handler
def add_globals_hook():

    heating_schedule = {}
    alarm_schedule = {}

    global_vars = {
        'alarm_armed': False,
        'heating_schedule': heating_schedule,
        'alarm_schedule': alarm_schedule
    }

    data = web.storage(global_vars)

    def _wrapper(handler):
        web.ctx.globals = data
        return handler()

    return _wrapper


# setting up the server
app = web.application(urls, globals())
app.add_processor(add_globals_hook())


# handles requests to /
class IndexHandler:
    def __init__(self):
        print 'Initializing IndexHandler...'

    # GET /
    def GET(self):
        print 'GET: %s' % self.__class__
        data = web.ctx.globals
        print data.alarm_armed
        return "Hello world!"

    # POST /
    def POST(self):
        print web.data()
        print 'POST: %s' % self.__class__
        return "POST DONE"


# handles requests to /Temperature
class TemperatureHandler:
    def __init__(self):
        print 'Initializing TemperatureHandler...'

    # GET /Temperature
    def GET(self):
        print 'GET: %s' % self.__class__

        temperature = random.random()
        date = datetime.datetime.now()
        data = {'temp': temperature, 'date': date}

        print 'DATE:\t\t\t%s' % date
        print 'TEMPERATURE:\t%s' % temperature

        return json.dumps(data, cls=DateTimeEncoder)

    # POST /Temperature
    def POST(self):
        print 'POST: %s' % self.__class__

        data = json.loads(web.data())
        print data['name']

        return "POST DONE"


# handles requests to /Alarm
class AlarmHandler:
    def __init__(self):
        print 'Initializing AlarmHandler...'

    # GET /Alarm
    def GET(self):
        print 'GET: %s' % self.__class__
        data = web.ctx.globals
        return 'Alarm Armed: %s' % str(data.alarm_armed)

    # POST /Alarm
    def POST(self):
        print 'POST: %s' % self.__class__

        data = json.loads(web.data())

        if 'arm' in data:
            glob = web.ctx.globals
            glob.alarm_armed = data['arm']


# custom JSON encoder for dealing with datetime objects
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
