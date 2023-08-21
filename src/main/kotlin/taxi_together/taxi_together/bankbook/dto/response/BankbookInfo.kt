package taxi_together.taxi_together.bankbook.dto.response

import taxi_together.taxi_together.bankbook.domain.Bank

data class BankbookInfo(val bank: Bank, val accountNumber: String)