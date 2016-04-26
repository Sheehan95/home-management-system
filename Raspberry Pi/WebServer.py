import json
import os
import threading
import time
import web

from CustomJSONEncoder import CustomJSONEncoder
from datetime import datetime, timedelta
from FakeSensors import MotionSensor, TemperatureSensor
from Task import TaskScheduler, TaskType
from twython import Twython


# defining twitter keys & tokens
APP_KEY = '7ezAHp8AV4sW2J0xHXlwR5nMX'
APP_SECRET = 'UlSHZjpQKPyCLIic0MWhynNc0oqZsP8vK3lBcKKEPlKtmIhLCN'
OAUTH_TOKEN = '724683684406763520-chyWXTf06o20tziTj7Uy90Fy6Wz0fYg'
OAUTH_SECRET = 'm94MqZIShE6e9W6BFw9TmXViq11TDxf5MZsYDIU9kdJLU'
TWITTER_DATA_FILE = 'twitter.txt'

twitter = Twython(APP_KEY, APP_SECRET, OAUTH_TOKEN, OAUTH_SECRET)

twitter_id = None


# defining urls handled by the server
urls = (
    '/',                        'IndexHandler',
    '/Temperature',             'TemperatureHandler',
    '/Alarm',                   'AlarmHandler',
    '/Schedule',                'ScheduleHandler',
    '/Schedule/Cancel/(.*)',    'ScheduleCancelHandler',
    '/Twitter/(.*)',            'TwitterHandler',
    '/Twitter',                 'TwitterHandler',
    '/Camera',                  'CameraHandler',
    '/BreakIn',                 'BreakInHandler'
)


# defining global variables
alarm_armed = False
heating_on = False
break_in = False
scheduler = TaskScheduler()
last_break_in = None

temperature_sensor = TemperatureSensor()
motion_sensor = MotionSensor()

# setting up the server
app = web.application(urls, globals())


########################################################################################################################
# handles requests to /
class IndexHandler:

    def __init__(self):
        print 'Initializing IndexHandler...'

    # GET /
    def GET(self):
        global alarm_armed
        global heating_on
        global scheduler

        schedule = ''

        for task in scheduler.scheduled:
            schedule += '{0} to run at {1}\n'.format(task.task_type, task.date)

        status = 'STATUS:\n\n' \
                 'Alarm Armed:\t{0}\n' \
                 'Break In:\t{1}\n' \
                 'Heating:\t{2}\n' \
                 'Twitter ID:\t{3}\n' \
                 'Scheduled:\n{4}'.format(alarm_armed, break_in, heating_on, twitter_id, schedule)

        return status


# handles requests to /Temperature
class TemperatureHandler:

    def __init__(self):
        print 'Initializing TemperatureHandler...'

    # GET /Temperature
    def GET(self):
        global heating_on

        temperature = temperature_sensor.temperature
        date = datetime.now()

        data = {
            'temperature': temperature,
            'date': date,
            'heating': heating_on
        }

        return json.dumps(data, cls=CustomJSONEncoder)

    # POST /Temperature
    def POST(self):
        if not web.data():
            raise web.BadRequest

        data = json.loads(web.data())

        if 'heating_on' in data:
            global heating_on
            heating_on = data['heating_on']


# handles requests to /Alarm
class AlarmHandler:

    def __init__(self):
        print 'Initializing AlarmHandler...'

    # GET /Alarm
    def GET(self):
        global alarm_armed
        global break_in
        data = {
            'alarm_armed': alarm_armed,
            'break_in': break_in
        }
        return json.dumps(data, cls=CustomJSONEncoder)

    # POST /Alarm
    def POST(self):
        if not web.data():
            raise web.BadRequest

        data = json.loads(web.data())

        global alarm_armed
        if 'arm' in data:
            alarm_armed = data['arm']

        global break_in
        if 'ack' in data:
            if data['ack']:
                break_in = False


# handles requests to /Schedule
class ScheduleHandler:

    def __init__(self):
        print 'Initializing ScheduleHandler'

    # GET /Schedule
    def GET(self):
        global scheduler
        return json.dumps(scheduler.scheduled, cls=CustomJSONEncoder)

    # POST /Schedule
    def POST(self):
        if not web.data():
            raise web.BadRequest

        global scheduler

        data = json.loads(web.data())
        function = None

        date = datetime.strptime(data['date'], '%d/%m/%Y %H:%M:%S')
        task_type = data['task_type']

        if task_type == TaskType.ARM_ALARM:
            function = arm_alarm
        elif task_type == TaskType.DISARM_ALARM:
            function = disarm_alarm
        elif task_type == TaskType.TURN_ON_HEATING:
            function = turn_on_heating
        elif task_type == TaskType.TURN_OFF_HEATING:
            function = turn_off_heating

        # if the date has already occurred or no function has been selected
        if date <= datetime.now() or function is None:
            return web.badrequest()
        else:
            scheduler.add_task(function, date, task_type)


# handles requests to /Schedule/Cancel/{id}
class ScheduleCancelHandler:

    def __init__(self):
        print 'Initializing ScheduleCancelHandler...'

    def POST(self, task_id):
        global scheduler

        for task in scheduler.scheduled:

            task_id = int(task_id)

            print 'Comparing {0} & {1}'.format(task_id, task.task_id)
            print task

            if task.task_id == task_id:
                print 'Found it'
                scheduler.remove_task(task)
                return

        raise web.notfound()


# handles requests to /Twitter
class TwitterHandler:

    def __init__(self):
        print 'Initializing TwitterHandler...'

    def GET(self):
        return twitter_id

    def POST(self, new_id):
        write_twitter_id(new_id)


# handles requests to /Camera
class CameraHandler:

    def __init__(self):
        print 'Initializing CameraHandler...'

    def GET(self):
        print 'Hello'


class BreakInHandler:

    def __init__(self):
        print 'Initializing BreakInHandler...'

    def GET(self):
        global break_in
        return break_in

    def POST(self):
        global break_in
        break_in = True
########################################################################################################################


# arms the alarm
def arm_alarm():
    print 'ARMING ALARM'
    global alarm_armed
    alarm_armed = True


# disarms the alarm
def disarm_alarm():
    print 'DISARMING ALARM'
    global alarm_armed
    alarm_armed = False


# turns on the heating
def turn_on_heating():
    print 'turn_on_heating'
    global heating_on
    heating_on = True


# turns off the heating
def turn_off_heating():
    print 'turn_off_heating'
    global heating_on
    heating_on = False


# sends a direct message to the user via Twitter
def twitter_report_break_in():
    message = 'There was a break-in detected on {0}'.format(datetime.now())
    twitter.send_direct_message(user_id=twitter_id, text=message)


# reads the user's twitter id from the file
def read_twitter_id():
    # if the file doesn't exist, create it
    if not os.path.exists(TWITTER_DATA_FILE):
        open (TWITTER_DATA_FILE, 'a')

    with open(TWITTER_DATA_FILE, 'r') as f:
        return f.read()


# writes the user's twitter id to the file
def write_twitter_id(new_id):
    # if the file doesn't exist, create it
    if not os.path.exists(TWITTER_DATA_FILE):
        open(TWITTER_DATA_FILE, 'a')

    with open(TWITTER_DATA_FILE, 'w') as f:
        f.write(new_id)

    # read the new value into the twitter_id
    global twitter_id
    twitter_id = read_twitter_id()


# checks to see if a break-in has occurred
def monitor():

    # allow time for server to start up
    time.sleep(5)

    while True:
        req = app.request('/Alarm', method='GET')
        data = json.loads(req.data)

        if data['break_in']:
            global last_break_in

            # initializes variable if it hasn't already been set
            if last_break_in is None:
                last_break_in = datetime.now()
                # sets the threshold to a time that has already occurred
                threshold = datetime.now() - timedelta(hours=1)
            else:
                # cannot trigger a break-in unless a minute has elapsed
                threshold = last_break_in + timedelta(minutes=1)

            if datetime.now() > threshold:
                print 'NEW BREAK IN DETECTED'
                # sets the value for the next pass
                last_break_in = datetime.now()

        time.sleep(10)


# equivalent to public static void main
if __name__ == "__main__":
    t = threading.Thread(target=monitor)
    t.daemon = True
    t.start()

    app.request('/Twitter/{0}'.format(read_twitter_id()), method='POST')
    app.run()