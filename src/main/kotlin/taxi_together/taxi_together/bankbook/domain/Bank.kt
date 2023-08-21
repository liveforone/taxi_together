package taxi_together.taxi_together.bankbook.domain

import taxi_together.taxi_together.exception.exception.BankbookException
import taxi_together.taxi_together.exception.message.BankbookExceptionMessage

enum class Bank {
    POST_OFFICE, IBK, KDB, NH, SH, KB,
    HANA, SHINHAN, SC, WOORI, K_BANK,
    KAKAO, TOOS, SHINHUB, MG;

    companion object {
        fun create(bank: String): Bank =
            entries.find { it.name.equals(bank, ignoreCase = true) } ?: throw BankbookException(BankbookExceptionMessage.NOT_EXIST_BANK)
    }
}