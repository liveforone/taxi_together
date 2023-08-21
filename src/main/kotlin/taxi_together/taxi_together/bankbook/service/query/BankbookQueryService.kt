package taxi_together.taxi_together.bankbook.service.query

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.bankbook.repository.BankbookRepository
import java.util.UUID

@Service
@Transactional(readOnly = true)
class BankbookQueryService @Autowired constructor(
    private val bankbookRepository: BankbookRepository
) {
    fun getBankbookByMember(memberUUID: UUID) = bankbookRepository.findOneDtoByMemberUUID(memberUUID)
}