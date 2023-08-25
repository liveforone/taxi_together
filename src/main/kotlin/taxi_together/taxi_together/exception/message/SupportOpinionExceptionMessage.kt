package taxi_together.taxi_together.exception.message

enum class SupportOpinionExceptionMessage(val status: Int, val message: String) {
    NO_VALID_TYPE_EXIST(404, "입력값에 해당하는 지원 타입이 존재하지 않습니다."),
    SUPPORT_OPINION_IS_NULL(404, "지원 의견이 존재하지 않습니다.")
}