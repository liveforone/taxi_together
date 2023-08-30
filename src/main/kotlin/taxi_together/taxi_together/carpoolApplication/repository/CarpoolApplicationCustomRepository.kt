package taxi_together.taxi_together.carpoolApplication.repository

import taxi_together.taxi_together.carpoolApplication.domain.CarpoolApplication
import taxi_together.taxi_together.carpoolApplication.dto.response.CarpoolApplicationBelongCarpoolInfo
import taxi_together.taxi_together.carpoolApplication.dto.response.CarpoolApplicationBelongMemberInfo
import java.util.UUID

interface CarpoolApplicationCustomRepository {
    fun findOneByCarpoolIdAndMemberUUID(carpoolId: Long, memberUUID: UUID): CarpoolApplication
    fun findCarpoolApplicationsByCarpoolId(carpoolId: Long): List<CarpoolApplicationBelongCarpoolInfo>
    fun findCarpoolApplicationsByMemberUUID(memberUUID: UUID): List<CarpoolApplicationBelongMemberInfo>
    fun countCarpoolApplicationByCarpoolId(carpoolId: Long): Long
}