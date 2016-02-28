import BaseHTTPServer
import random

HOST_NAME = '127.0.0.1'
PORT_NUMBER = 8080


class MyHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_HEAD(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()

    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")

        if self.path == '/':
            print "Index"

        elif self.path == '/temp':
            self.send_header('temperature', random.random())

        elif self.path == '/motion':
            self.send_header('motion', random.random())

        else:
            print "404"

        self.end_headers()


if __name__ == '__main__':
    server_class = BaseHTTPServer.HTTPServer
    httpd = server_class((HOST_NAME, PORT_NUMBER), MyHandler)

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass

    httpd.server_close()
