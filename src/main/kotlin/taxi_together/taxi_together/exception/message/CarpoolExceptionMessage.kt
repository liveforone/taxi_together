package taxi_together.taxi_together.exception.message

enum class CarpoolExceptionMessage(val status: Int, val message: String) {
    CARPOOL_IS_NULL(404, "카풀이 존재하지 않습니다."),
    IN_REMOVE_CARPOOL_IS_NOT_ZERO(400, "카풀을 신청한 회원이 있어 삭제가 불가능합니다.")
}