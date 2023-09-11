class CubicBezierCurve:
    p0, p1, p2, p3 = None, None, None, None

    def __init__(self, p0, p1, p2, p3):
        self.p0, self.p1, self.p2, self.p3 = p0, p1, p2, p3

    def getP0(self):
        return self.p0
    
    def setP0(self, p0):
        self.p0 = p0

    def getP1(self):
        return self.p1
    
    def setP1(self, p1):
        self.p1 = p1

    def getP2(self):
        return self.p2
    
    def setP2(self, p2):
        self.p2 = p2

    def getP3(self):
        return self.p3
    
    def setP3(self, p3):
        self.p3 = p3

    def calculate(self, t: float):
        # (1 - t)^3 * P0 + 3 * (1 - t)^2 * t * P1 + 3 * (1 - t) * t^2 * P2 + t^3 * P3
        w = 1 - t
        firstTerm = self.p0.scalarMultiply(w * w * w)
        secondTerm = self.p1.scalarMultiply(3 * w * w * t)
        thirdTerm = self.p2.scalarMultiply(3 * w * t * t)
        fourthTerm = self.p3.scalarMultiply(t * t * t)

        return firstTerm.add(secondTerm).add(thirdTerm).add(fourthTerm)
    
    def derivative(self, t: float):
        # 3 * (1 - t)^2 * (P1 - P0) + 6 * (1 - t) * t * (P2 - P1) + 3 * t^2 * (P3 - P2)
        w = 1 - t
        firstTerm = self.p1.subtract(self.p0).scalarMultiply(3 * w * w)
        secondTerm = self.p2.subtract(self.p1).scalarMultiply(6 * w * t)
        thirdTerm = self.p3.subtract(self.p2).scalarMultiply(3 * t * t)

        return firstTerm.add(secondTerm).add(thirdTerm)