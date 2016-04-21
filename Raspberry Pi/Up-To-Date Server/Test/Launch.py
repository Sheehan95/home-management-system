import httplib
import json
import datetime
import time


class DateTimeDecoder(json.JSONDecoder):

    def __init__(self, *args, **kargs):
        json.JSONDecoder.__init__(self, object_hook=self.dictionary_to_object,
                                  *args, **kargs)

    def dictionary_to_object(self, dictionary):
        if '__type__' not in dictionary:
            return dictionary

        type = dictionary.pop('__type__')

        try:
            dateobj = datetime.datetime(**dictionary)
            return dateobj
        except:
            dictionary['__type__'] = type
            return dictionary

if __name__ == "__main__":
    while True:
        conn = httplib.HTTPConnection('localhost:8080')
        conn.request('GET', '/temperature')
        res = conn.getresponse()
        data = json.loads(res.read(), cls=DateTimeDecoder)
        print 'DATE:\t%s' % data['date']
        print 'TEMP:\t%s' % data['temp']
        print '------------------------'
        time.sleep(2.5)
