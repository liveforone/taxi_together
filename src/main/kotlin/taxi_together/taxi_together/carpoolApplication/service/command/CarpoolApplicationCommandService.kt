package taxi_together.taxi_together.carpoolApplication.service.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.carpoolApplication.domain.CarpoolApplication
import taxi_together.taxi_together.carpoolApplication.dto.request.CancelCarpoolApplication
import taxi_together.taxi_together.carpoolApplication.dto.request.CreateCarpoolApplication
import taxi_together.taxi_together.carpoolApplication.repository.CarpoolApplicationRepository
import taxi_together.taxi_together.carpoolApplication.service.validator.CarpoolApplicationValidator

@Service
@Transactional
class CarpoolApplicationCommandService @Autowired constructor(
    private val carpoolApplicationValidator: CarpoolApplicationValidator,
    private val carpoolApplicationRepository: CarpoolApplicationRepository
) {
    fun createCarpoolApplication(createCarpoolApplication: CreateCarpoolApplication) {
        with(createCarpoolApplication) {
            carpoolApplicationValidator.validateCarpoolStateAndDate(carpoolId!!)
            carpoolApplicationValidator.validateExcessiveCarpool(carpoolId)
            CarpoolApplication.create(carpoolId, memberUUID!!)
                .also { carpoolApplicationRepository.save(it) }
        }
    }

    fun cancelCarpoolApplication(cancelCarpoolApplication: CancelCarpoolApplication) {
        with(cancelCarpoolApplication) {
            carpoolApplicationValidator.validateCarpoolStateAndDate(carpoolId!!)
            carpoolApplicationRepository.findOneByCarpoolIdAndMemberUUID(carpoolId, memberUUID!!)
                .also { carpoolApplicationRepository.delete(it) }
        }
    }
}