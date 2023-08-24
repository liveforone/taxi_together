package taxi_together.taxi_together.exception.message

enum class CarpoolApplicationExceptionMessage(val status: Int, val message: String) {
    CARPOOL_IS_COMPLETED(400, "이미 완료된 카풀입니다."),
    OVER_PICK_DATE(400, "픽업 시간이 지난 카풀입니다."),
    CARPOOL_OVERPASSES(400, "카풀 승차 인원을 초과하였습니다."),
    CARPOOL_APPLICATION_IS_NULL(404, "카풀 신청이 존재하지 않습니다.")
}