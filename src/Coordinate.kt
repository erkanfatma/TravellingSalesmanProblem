import kotlin.math.sqrt

//To define 2-dimensions of coordinate system that consist of x coordinate and y coordinate.
class Coordinate internal constructor(var xCoordinate: Int, var yCoordinate: Int) {

    // To calculate distance with instance coordinate and given coordinate
    fun DistanceCalculator(point: Coordinate): Double {
        return sqrt(((xCoordinate - point.xCoordinate) * (xCoordinate - point.xCoordinate) + (yCoordinate - point.yCoordinate)).toDouble() * (yCoordinate - point.yCoordinate).toDouble())
    }

}