package taxi_together.taxi_together.exception.message

enum class BankbookExceptionMessage(val status: Int, val message: String) {
    NOT_EXIST_BANK(404, "해당하는 은행이 존재하지 않습니다."),
    BANKBOOK_IS_NULL(404, "계좌가 존재하지 않습니다.")
}