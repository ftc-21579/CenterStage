import math


class Vector2:

    x = 0.0
    y = 0.0

    def __init__(self, x: float, y: float):
        self.x = x
        self.y = y

    def getX(self):
        return self.x
    
    def getY(self):
        return self.y
    
    def setX(self, x: float):
        self.x = x

    def setY(self, y: float):
        self.y = y
    
    def getHeading(self):
        return math.atan2(self.y, self.x)
    
    def getMagnitude(self):
        return math.hypot(self.x, self.y)
    
    def getMagSq(self):
        return self.x * self.x + self.y * self.y

    def add(self, vector: Vector2):
        return Vector2(self.x + vector.x, self.y + vector.y)
    
    def subtract(self, vector: Vector2):
        return Vector2(self.x - vector.x, self.y - vector.y)
    
    def scalarMultiply(self, scalar: float):
        return Vector2(self.x * scalar, self.y * scalar)
    
    def scalarDivide(self, scalar: float):
        return Vector2(self.x / scalar, self.y / scalar)