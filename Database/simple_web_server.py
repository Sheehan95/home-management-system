import web
from random import randint

urls = (
    '/', 'index')

class index:
    
    def GET(self):
        return str(randint(0, 9))

    if __name__ == "__main__":
        app = web.application(urls, globals());
        app.run()

        
