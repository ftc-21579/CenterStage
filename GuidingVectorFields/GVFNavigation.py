import datetime
import math

from CubicBezierCurve import CubicBezierCurve
from Vector2 import Vector2

class GVFNavigation:

    curve, currentLocation = None, None

    def __init__(self, curve, currentLocation):
        self.curve = curve
        self.currentLocation = currentLocation


    def calculateGuidanceVector(self, curve: CubicBezierCurve, currentLocation: Vector2):
        startTime = datetime.datetime.now()
        closestT = GVFNavigation.findClosestPoint(curve, currentLocation)
        closestPoint = curve.calculate(closestT)
        curveDerivative = curve.derivative(closestT)
        robotToClosestPoint = closestPoint.subtract(currentLocation)
        robotToClosestPointMag = robotToClosestPoint.getMag()

        CORRECTION_DISTANCE = 100

        endPoint = curve.calculate(1)
        SAVING_THROW_DISTANCE = 100
        directPursuitThreshold = 1

        for i in range(100, -1, -1):
            i /= 100.0

            dist = endPoint.subtract(curve.calculate(i)).getMagSq()

            if(dist < SAVING_THROW_DISTANCE):
                directPursuitThreshold = i
                break

        robotToEnd = endPoint.subtract(currentLocation)

        correctionFactor = math.min(1, robotToClosestPoint.getMagnitude() / CORRECTION_DISTANCE)
        movementDirection = GVFNavigation.hlerp(curveDerivative.getHeading(), robotToClosestPoint.getHeading(), correctionFactor)

        if ((closestT == 1 and math.abs(currentLocation.subtract(closestPoint).getHeading() - curveDerivative.getHeading()) <= 0.5 * math.pi) or closestT >= directPursuitThreshold):
            movementDirection = endPoint.subtract(currentLocation).getHeading()

        movementVector = Vector2(math.cos(movementDirection), math.sin(movementDirection))
        speed = 1

        if (robotToEnd.getMagnitude() < 200):
            speed = GVFNavigation.lerp(0.2, speed, robotToEnd.getMagnitude() / 200.0)
        
        return movementVector

    
    def hlerp(a: float, b:float, t: float):
        diff = b - a
        diff %= 2 * math.pi

        if(math.abs(diff) > math.pi):
            if(diff > 0):
                diff -= 2 * math.pi
            else:
                diff += 2 * math.pi
        return a + t * diff
    
    def lerp(a: float, b: float, t: float):
        return a + t * (b - a)
    
    def calculateMinimizationFunction(curve: CubicBezierCurve, t: float, point: Vector2):
        return curve.calculate(t).subtract(point).getMagSq()

    def findClosestPoint(curve: CubicBezierCurve, point: Vector2):
        minT = -1
        minDist = float('inf')
        SAMPLE_DENSITY = 100

        for i in range(0, SAMPLE_DENSITY):
            t = i / SAMPLE_DENSITY
            dist = GVFNavigation.calculateMinimizationFunction(curve, t, point)

            if(dist < minDist):
                minDist = dist
                minT = t
        
        return minT