package taxi_together.taxi_together.carpool.service.query

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.carpool.repository.CarpoolRepository
import java.util.UUID

@Service
@Transactional(readOnly = true)
class CarpoolQueryService @Autowired constructor(
    private val carpoolRepository: CarpoolRepository
) {
    fun getCarpoolByUUID(uuid: UUID) = carpoolRepository.findOneDtoByUUID(uuid)
    fun getCarpools(currLatitude: Double, currLongitude: Double, lastUUID: UUID?) =
        carpoolRepository.findCarpools(currLatitude, currLongitude, lastUUID)
}