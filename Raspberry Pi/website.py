import web
from mpl3115a2 import sensor

urls = (
    '/', '/index'
    )

class index:
    def GET(self):
        return mpl.getTemp()

    if __name__ == "__main__":

        mpl = sensor()
        mpl.initAlt()
        mpl.active()
        
        app = web.application(urls, globals())
        app.run()
