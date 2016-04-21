class Person:

    def __init__(self):
        self.name = ""
        self.age = 0

    def introduce(self, person):
        print 'My name is %s. This is %s. He is %s years old.' % (self.name, person.name, person.age)
