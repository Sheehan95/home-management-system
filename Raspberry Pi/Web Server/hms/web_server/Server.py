import BaseHTTPServer

from hms.web_server.Sensors import TemperatureSensor, MotionSensor

HOST_NAME = '127.0.0.1'
PORT_NUMBER = 8080

# class that extends the BaseHTTPRequestHandler class


class WebServer(BaseHTTPServer.BaseHTTPRequestHandler):

    # URLS being served
    INDEX = '/'
    TEMPERATURE = '/temperature'
    MOTION = '/motion'
    ALARM = '/alarm'

    armed = False

    # initializing sensors for reading data
    temp_sensor = TemperatureSensor()
    motion_sensor = MotionSensor()

    def do_HEAD(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        self.send_response(200)

        if self.path == WebServer.TEMPERATURE:
            self.send_header('temperature', WebServer.temp_sensor.get_temp())

        elif self.path == WebServer.MOTION:
            coordinates = WebServer.motion_sensor.get_coordinates()
            self.send_header('x', coordinates['x'])
            self.send_header('y', coordinates['y'])
            self.send_header('z', coordinates['z'])

        elif self.path == WebServer.ALARM:
            self.send_header("alarm", WebServer.armed)

        self.end_headers()

    def do_POST(self):
        self.send_response(200)

        if self.path == WebServer.ALARM:
            status = self.headers.getheader("alarm", "default")
            if status == 'arm':
                WebServer.armed = True
            elif status == 'disarm':
                WebServer.armed = False

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
