import BaseHTTPServer

from hms.web_server.Sensors import TemperatureSensor, MotionSensor

HOST_NAME = '127.0.0.1'
PORT_NUMBER = 8080

# class that extends the BaseHTTPRequestHandler class
class WebServer(BaseHTTPServer.BaseHTTPRequestHandler):

    # String constants for the URLs being served
    INDEX = '/'
    TEMPERATURE = '/temperature'
    MOTION = '/motion'

    # initializing sensors for reading data
    temp_sensor = TemperatureSensor()
    motion_sensor = MotionSensor()

    # overwritten function
    def do_HEAD(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    # overwritten function that handles HTTP GET requests
    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')

        # the path is the URL being viewed. if path == '/temperature', the URL is localhost:8080/temperature
        if self.path == WebServer.INDEX:
            print "Index"

        elif self.path == WebServer.TEMPERATURE:
            # adds the key-value pair to the header
            self.send_header('temperature', WebServer.temp_sensor.get_temp())

        elif self.path == WebServer.MOTION:
            coordinates = WebServer.motion_sensor.get_coordinates()
            self.send_header('x', coordinates['x'])
            self.send_header('y', coordinates['y'])
            self.send_header('z', coordinates['z'])

        else:
            print "404"

        self.end_headers()

# equivalent to the main function
if __name__ == '__main__':
    server_class = BaseHTTPServer.HTTPServer
    httpd = server_class((HOST_NAME, PORT_NUMBER), WebServer)

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass

    httpd.server_close()
