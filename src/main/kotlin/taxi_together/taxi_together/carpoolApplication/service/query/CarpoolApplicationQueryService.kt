package taxi_together.taxi_together.carpoolApplication.service.query

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.carpoolApplication.repository.CarpoolApplicationRepository
import java.util.UUID

@Service
@Transactional(readOnly = true)
class CarpoolApplicationQueryService @Autowired constructor(
    private val carpoolApplicationRepository: CarpoolApplicationRepository
) {
    fun getCarpoolApplicationsByCarpoolId(carpoolId: Long) =
        carpoolApplicationRepository.findCarpoolApplicationsByCarpoolId(carpoolId)

    fun getCarpoolApplicationsByMemberUUID(memberUUID: UUID) =
        carpoolApplicationRepository.findCarpoolApplicationsByMemberUUID(memberUUID)
}