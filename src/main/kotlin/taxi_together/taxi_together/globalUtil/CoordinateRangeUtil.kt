package taxi_together.taxi_together.globalUtil

const val HUNDRED_METER: Int = 100
const val FIVE_HUNDRED_METER: Int = 500
const val ONE_KILOMETER: Int = 1000
const val THREE_KILOMETER: Int = 3000

private fun calculateRadiusDegree(radiusMeters: Int): Double = (radiusMeters / 111320.0)

fun calculateCoordinateLatitudeRange(latitude: Double, radiusMeters: Int): ClosedRange<Double> {
    val radiusDegree = calculateRadiusDegree(radiusMeters)
    return (latitude - radiusDegree).coerceAtLeast(-90.0)..(latitude + radiusDegree).coerceAtMost(90.0)
}

fun calculateCoordinateLongitudeRange(longitude: Double, radiusMeters: Int): ClosedRange<Double> {
    val radiusDegree = calculateRadiusDegree(radiusMeters)
    return (longitude - radiusDegree).coerceAtLeast(-180.0)..(longitude + radiusDegree).coerceAtMost(180.0)
}