package taxi_together.taxi_together.bankbook.repository

import org.springframework.data.jpa.repository.JpaRepository
import taxi_together.taxi_together.bankbook.domain.Bankbook

interface BankbookRepository : JpaRepository<Bankbook, Long>, BankbookCustomRepository