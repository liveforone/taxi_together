package taxi_together.taxi_together.carpoolApplication.repository

import taxi_together.taxi_together.carpoolApplication.domain.CarpoolApplication
import taxi_together.taxi_together.carpoolApplication.dto.response.CarpoolApplicationBelongCarpoolInfo
import taxi_together.taxi_together.carpoolApplication.dto.response.CarpoolApplicationBelongMemberInfo
import java.util.UUID

interface CarpoolApplicationCustomRepository {
    fun findOneByCarpoolUUIDAndMemberUUID(carpoolUUID: UUID, memberUUID: UUID): CarpoolApplication
    fun findCarpoolApplicationsByCarpoolUUID(carpoolUUID: UUID): List<CarpoolApplicationBelongCarpoolInfo>
    fun findCarpoolApplicationsByMemberUUID(memberUUID: UUID): List<CarpoolApplicationBelongMemberInfo>
    fun countCarpoolApplicationByCarpoolUUID(carpoolUUID: UUID): Long
}