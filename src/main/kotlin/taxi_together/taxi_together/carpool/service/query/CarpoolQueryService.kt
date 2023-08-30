package taxi_together.taxi_together.carpool.service.query

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.carpool.repository.CarpoolRepository

@Service
@Transactional(readOnly = true)
class CarpoolQueryService @Autowired constructor(
    private val carpoolRepository: CarpoolRepository
) {
    fun getCarpoolById(id: Long) = carpoolRepository.findOneDtoById(id)
    fun getCarpools(currLatitude: Double, currLongitude: Double, lastId: Long?) =
        carpoolRepository.findCarpools(currLatitude, currLongitude, lastId)
}