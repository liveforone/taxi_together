package taxi_together.taxi_together.carpool.repository

import taxi_together.taxi_together.carpool.domain.Carpool
import taxi_together.taxi_together.carpool.dto.response.CarpoolInfo

interface CarpoolCustomRepository {
    fun findOneById(id: Long): Carpool
    fun findOneDtoById(id: Long): CarpoolInfo
    fun findCarpools(currLatitude: Double, currLongitude: Double, lastId: Long?): List<CarpoolInfo>
}