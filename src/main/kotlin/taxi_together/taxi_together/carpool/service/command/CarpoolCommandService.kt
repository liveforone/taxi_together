package taxi_together.taxi_together.carpool.service.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.carpool.domain.Carpool
import taxi_together.taxi_together.carpool.dto.request.CreateCarpool
import taxi_together.taxi_together.carpool.dto.request.RemoveCarpool
import taxi_together.taxi_together.carpool.dto.update.CalculateCarpool
import taxi_together.taxi_together.carpool.repository.CarpoolRepository
import taxi_together.taxi_together.carpool.service.validator.CarpoolServiceValidator
import taxi_together.taxi_together.carpoolApplication.repository.CarpoolApplicationRepository
import taxi_together.taxi_together.member.repository.MemberRepository

@Service
@Transactional
class CarpoolCommandService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val carpoolRepository: CarpoolRepository,
    private val carpoolApplicationRepository: CarpoolApplicationRepository,
    private val carpoolServiceValidator: CarpoolServiceValidator
) {
    fun createCarpool(createCarpool: CreateCarpool): Long {
        return with(createCarpool) {
            Carpool.create(
                member = memberRepository.findOneByUUID(memberUUID!!),
                pickupLatitude!!,
                pickupLongitude!!,
                month!!,
                day!!,
                hour!!,
                minute!!,
                destination!!
            ).run { carpoolRepository.save(this).id!! }
        }
    }

    fun calculateCarpool(calculateCarpool: CalculateCarpool) {
        with(calculateCarpool) {
            carpoolRepository.findOneById(id!!)
                .also {
                    it.calculateCarpool(
                        totalFare!!,
                        passengerCount = carpoolApplicationRepository.countCarpoolApplicationByCarpoolId(id).toInt()
                    ) }
        }
    }

    fun removeCarpool(removeCarpool: RemoveCarpool) {
        with(removeCarpool) {
            carpoolServiceValidator.validateCountOfCarpoolIsZero(id!!)
            carpoolRepository.findOneById(id).also { carpoolRepository.delete(it) }
        }
    }
}