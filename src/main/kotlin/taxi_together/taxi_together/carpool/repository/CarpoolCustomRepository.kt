package taxi_together.taxi_together.carpool.repository

import taxi_together.taxi_together.carpool.domain.Carpool
import taxi_together.taxi_together.carpool.dto.response.CarpoolInfo
import java.util.UUID

interface CarpoolCustomRepository {
    fun findOneByUUID(uuid: UUID): Carpool
    fun findOneDtoByUUID(uuid: UUID): CarpoolInfo
    fun findCarpools(currLatitude: Double, currLongitude: Double, lastUUID: UUID?): List<CarpoolInfo>
}