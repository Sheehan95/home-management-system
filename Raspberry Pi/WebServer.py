import json
import threading
import time
import web
import os
from twython import Twython

from datetime import datetime
from CustomJSONEncoder import CustomJSONEncoder
from Task import TaskScheduler, TaskType, InvalidScheduleDateError

from FakeSensors import TemperatureSensor, MotionSensor

urls = (
    '/',                        'IndexHandler',
    '/Temperature',             'TemperatureHandler',
    '/Alarm',                   'AlarmHandler',
    '/Schedule',                'ScheduleHandler',
    '/Schedule/Cancel/(.*)',    'ScheduleCancelHandler',
    '/Camera',                  'CameraHandler'
)

APP_KEY = '7ezAHp8AV4sW2J0xHXlwR5nMX'
APP_SECRET = 'UlSHZjpQKPyCLIic0MWhynNc0oqZsP8vK3lBcKKEPlKtmIhLCN'

alarm_armed = False
heating_on = False
scheduler = TaskScheduler()

temperature_sensor = TemperatureSensor()
motion_sensor = MotionSensor()

# setting up the server
app = web.application(urls, globals())


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

        return 'STATUS:\nAlarm Armed:\t{0}\nHeating On:\t{1}\nScheduled Tasks:\t{2}'.format(
            alarm_armed, heating_on, schedule)


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
        data = {'alarm_armed': alarm_armed}
        return json.dumps(data, cls=CustomJSONEncoder)

    # POST /Alarm
    def POST(self):
        if not web.data():
            raise web.BadRequest

        data = json.loads(web.data())

        if 'arm' in data:
            global alarm_armed
            alarm_armed = data['arm']


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

        try:
            scheduler.add_task(function, date, task_type)
        except InvalidScheduleDateError:
            return web.badrequest()


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


# handles requests to /Camera
class CameraHandler:

    def __init__(self):
        print 'Initializing CameraHandler...'

    def GET(self):
        filename = 'Wildlife.wmv'

#        if filename in os.listdir('../../../captures'):
#            yield open('../../../captures/{0}'.format(filename), 'rb').read()
#        else:
#            raise web.notfound()


def arm_alarm():
    print 'ARMING ALARM'
    global alarm_armed
    alarm_armed = True


def disarm_alarm():
    print 'DISARMING ALARM'
    global alarm_armed
    alarm_armed = False


def turn_on_heating():
    print 'turn_on_heating'
    global heating_on
    heating_on = True


def turn_off_heating():
    print 'turn_off_heating'
    global heating_on
    heating_on = False


def entry():
    while True:
        x = app.request('/Alarm', method='GET')
        y = json.loads(x.data)
        print y['alarm_armed']
        if y['alarm_armed']:
            print 'BREAK IN DETECTED'
        else:
            print 'All is well...'

        time.sleep(5)


def get_alarm_status():
    global alarm_armed
    return alarm_armed


# equivalent to public static void main
if __name__ == "__main__":
    t = threading.Thread(target=entry)
    t.daemon = True
    t.start()

    twitter = Twython(APP_KEY, APP_SECRET)
    twitter.update_status(status="Hello from Python!")

    app.run()
