package taxi_together.taxi_together.carpoolApplication.service.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import taxi_together.taxi_together.carpool.domain.CarpoolState
import taxi_together.taxi_together.carpool.repository.CarpoolRepository
import taxi_together.taxi_together.carpoolApplication.repository.CarpoolApplicationRepository
import taxi_together.taxi_together.carpoolApplication.service.constant.CarpoolApplicationServiceConstant
import taxi_together.taxi_together.exception.exception.CarpoolApplicationException
import taxi_together.taxi_together.exception.message.CarpoolApplicationExceptionMessage
import taxi_together.taxi_together.globalUtil.getDatetimeDigit
import java.time.LocalDateTime

@Component
class CarpoolApplicationValidator @Autowired constructor(
    private val carpoolRepository: CarpoolRepository,
    private val carpoolApplicationRepository: CarpoolApplicationRepository
) {
    fun validateCarpoolStateAndDate(carpoolId: Long) {
        val carpool = carpoolRepository.findOneById(carpoolId)
        check(carpool.carpoolState == CarpoolState.UNCOMPLETED) {
            throw CarpoolApplicationException(
                CarpoolApplicationExceptionMessage.CARPOOL_IS_COMPLETED
            )
        }
        check(carpool.pickupDate > getDatetimeDigit(LocalDateTime.now())) {
            throw CarpoolApplicationException(
                CarpoolApplicationExceptionMessage.OVER_PICK_DATE
            )
        }
    }

    fun validateExcessiveCarpool(carpoolId: Long) {
        val countOfCarpool = carpoolApplicationRepository.countCarpoolApplicationByCarpoolId(carpoolId)
        check(countOfCarpool < CarpoolApplicationServiceConstant.CAPABLE_NUM) {
            throw CarpoolApplicationException(
                CarpoolApplicationExceptionMessage.CARPOOL_OVERPASSES
            )
        }
    }
}