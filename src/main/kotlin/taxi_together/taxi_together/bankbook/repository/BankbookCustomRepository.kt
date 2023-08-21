package taxi_together.taxi_together.bankbook.repository

import taxi_together.taxi_together.bankbook.domain.Bankbook
import taxi_together.taxi_together.bankbook.dto.response.BankbookInfo
import java.util.UUID

interface BankbookCustomRepository {
    fun findOneByMemberUUID(memberUUID: UUID): Bankbook
    fun findOneDtoByMemberUUID(memberUUID: UUID): BankbookInfo
}