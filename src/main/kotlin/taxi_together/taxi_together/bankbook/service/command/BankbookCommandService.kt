package taxi_together.taxi_together.bankbook.service.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.bankbook.domain.Bankbook
import taxi_together.taxi_together.bankbook.dto.update.UpdateBankbook
import taxi_together.taxi_together.bankbook.repository.BankbookRepository
import taxi_together.taxi_together.member.domain.Member

@Service
@Transactional
class BankbookCommandService @Autowired constructor(
    private val bankbookRepository: BankbookRepository
) {
    fun createBankbook(member: Member, bank: String, accountNumber: String) {
        Bankbook.create(member, bank, accountNumber).also { bankbookRepository.save(it) }
    }

    fun updateBankbook(updateBankbook: UpdateBankbook) {
        with(updateBankbook) {
            bankbookRepository.findOneByMemberUUID(memberUUID!!).also { it.updateBankbook(bank!!, accountNumber!!) }
        }
    }
}