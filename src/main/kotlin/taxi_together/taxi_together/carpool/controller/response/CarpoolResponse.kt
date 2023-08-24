package taxi_together.taxi_together.carpool.controller.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import taxi_together.taxi_together.carpool.dto.response.CarpoolInfo

object CarpoolResponse {
    private const val CREATE_SUCCESS = "카풀을 성공적으로 등록하였습니다."
    private const val CALCULATE_SUCCESS = "카풀을 성공적으로 정산하였습니다."
    private const val REMOVE_SUCCESS = "카풀을 성공적으로 삭제하였습니다."

    fun detailSuccess(carpoolInfo: CarpoolInfo) = ResponseEntity.ok(carpoolInfo)
    fun searchSuccess(carpools: List<CarpoolInfo>) = ResponseEntity.ok(carpools)
    fun createSuccess() = ResponseEntity.status(HttpStatus.CREATED).body(CREATE_SUCCESS)
    fun calculateSuccess() = ResponseEntity.ok(CALCULATE_SUCCESS)
    fun removeSuccess() = ResponseEntity.ok(REMOVE_SUCCESS)
}