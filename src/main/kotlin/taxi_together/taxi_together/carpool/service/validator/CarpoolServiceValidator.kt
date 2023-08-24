package taxi_together.taxi_together.carpool.service.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import taxi_together.taxi_together.carpoolApplication.repository.CarpoolApplicationRepository
import taxi_together.taxi_together.exception.exception.CarpoolException
import taxi_together.taxi_together.exception.message.CarpoolExceptionMessage
import java.util.UUID

@Component
class CarpoolServiceValidator @Autowired constructor(
    private val carpoolApplicationRepository: CarpoolApplicationRepository
) {
    fun validateCountOfCarpoolIsZero(carpoolUUID: UUID) {
        val count = carpoolApplicationRepository.countCarpoolApplicationByCarpoolUUID(carpoolUUID)
        check(count == 0.toLong()) { throw CarpoolException(CarpoolExceptionMessage.IN_REMOVE_CARPOOL_IS_NOT_ZERO) }
    }
}