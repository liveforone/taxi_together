package taxi_together.taxi_together.carpool.dto.response

import java.util.UUID

data class CarpoolInfo(val memberUUID: UUID, val pickupLatitude: Double, val pickupLongitude: Double, val pickupDate: Long, val destination: String, val individualFare: Int)