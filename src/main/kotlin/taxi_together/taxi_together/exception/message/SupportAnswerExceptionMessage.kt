package taxi_together.taxi_together.exception.message

enum class SupportAnswerExceptionMessage(val status: Int, val message: String) {
    ANSWER_IS_NULL(404, "지원 의견 답변이 존재하지 않습니다."),
    WRITER_IS_NOT_ADMIN(403, "운영자가 아닙니다.")
}