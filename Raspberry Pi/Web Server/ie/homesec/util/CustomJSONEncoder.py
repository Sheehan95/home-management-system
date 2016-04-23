import json
from datetime import datetime
from Task import Task


# custom JSON encoder
class CustomJSONEncoder(json.JSONEncoder):

    def default(self, obj):
        # parsing objects of type Task
        if isinstance(obj, Task):
            return {
                '__type__': 'task',
                'task_type': obj.task_type,
                'date': {
                    '__type__': 'datetime',
                    'year': obj.date.year,
                    'month': obj.date.month,
                    'day': obj.date.day,
                    'hour': obj.date.hour,
                    'minute': obj.date.minute,
                    'second': obj.date.second,
                    'microsecond': obj.date.microsecond
                }
            }
        # parsing objects of type Datetime
        elif isinstance(obj, datetime):
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
        # parsing with default behavior
        else:
            return json.JSONEncoder.default(self, obj)
