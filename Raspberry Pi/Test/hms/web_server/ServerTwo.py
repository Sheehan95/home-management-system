import BaseHTTPServer
import random

HOST_NAME = '127.0.0.1'
PORT_NUMER = 8080

class MyHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_HEAD(s):
        s.send_response(200)
        s.send_header("Content-type", "text/html")
        s.end_headers()

    def do_GET(s):
        s.send_response(200)
        s.send_header("Content-type", "text/html")

        s.wfile.write("Hello World!\n")

        if s.path == '/':
            print "Index"
        elif s.path == '/temp':
            s.send_header("Temperature", random.random())
        elif s.path == '/motion':
            s.send_header("Motion", random.random())
        else:
            print "404"

        s.end_headers()

if __name__ == '__main__':
    server_class = BaseHTTPServer.HTTPServer
    httpd = server_class((HOST_NAME, PORT_NUMER), MyHandler)

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass

    httpd.server_close()

