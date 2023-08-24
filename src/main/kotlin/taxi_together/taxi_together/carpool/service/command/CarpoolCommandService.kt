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
import java.util.UUID

@Service
@Transactional
class CarpoolCommandService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val carpoolRepository: CarpoolRepository,
    private val carpoolApplicationRepository: CarpoolApplicationRepository,
    private val carpoolServiceValidator: CarpoolServiceValidator
) {
    fun createCarpool(createCarpool: CreateCarpool): UUID {
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
            ).run { carpoolRepository.save(this).uuid }
        }
    }

    fun calculateCarpool(calculateCarpool: CalculateCarpool) {
        with(calculateCarpool) {
            carpoolRepository.findOneByUUID(uuid!!)
                .also {
                    it.calculateCarpool(
                        totalFare!!,
                        passengerCount = carpoolApplicationRepository.countCarpoolApplicationByCarpoolUUID(uuid).toInt()
                    ) }
        }
    }

    fun removeCarpool(removeCarpool: RemoveCarpool) {
        with(removeCarpool) {
            carpoolServiceValidator.validateCountOfCarpoolIsZero(uuid!!)
            carpoolRepository.findOneByUUID(uuid).also { carpoolRepository.delete(it) }
        }
    }
}