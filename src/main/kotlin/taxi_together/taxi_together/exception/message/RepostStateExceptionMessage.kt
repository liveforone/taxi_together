package taxi_together.taxi_together.exception.message

enum class RepostStateExceptionMessage(val status: Int, val message: String) {
    REPORT_STATE_IS_NULL(404, "신고 상태 정보가 존재하지 않습니다.")
}