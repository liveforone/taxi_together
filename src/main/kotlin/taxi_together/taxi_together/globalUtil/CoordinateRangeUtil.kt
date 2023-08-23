package taxi_together.taxi_together.globalUtil

fun calculateCoordinateLatitudeRange(latitude: Double): ClosedRange<Double> {
    return (latitude - 0.027).coerceAtLeast(-90.0)..(latitude + 0.027).coerceAtMost(90.0)
}

fun calculateCoordinateLongitudeRange(longitude: Double): ClosedRange<Double> {
    return (longitude - 0.027).coerceAtLeast(-180.0)..(longitude + 0.027).coerceAtMost(180.0)
}