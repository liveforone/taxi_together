package taxi_together.taxi_together.carpoolApplication.controller.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import taxi_together.taxi_together.carpoolApplication.dto.response.CarpoolApplicationBelongCarpoolInfo
import taxi_together.taxi_together.carpoolApplication.dto.response.CarpoolApplicationBelongMemberInfo

object CarpoolApplicationResponse {
    private const val CREATE_SUCCESS = "카풀 신청에 성공하였습니다."
    private const val CANCEL_SUCCESS = "카풀 신청 취소를 성공적으로 마쳤습니다."

    fun belongCarpoolSuccess(belongCarpoolInfo: List<CarpoolApplicationBelongCarpoolInfo>) = ResponseEntity.ok(belongCarpoolInfo)
    fun belongMemberSuccess(belongMemberInfo: List<CarpoolApplicationBelongMemberInfo>) = ResponseEntity.ok(belongMemberInfo)
    fun createSuccess() = ResponseEntity.status(HttpStatus.CREATED).body(CREATE_SUCCESS)
    fun cancelSuccess() = ResponseEntity.ok(CANCEL_SUCCESS)
}