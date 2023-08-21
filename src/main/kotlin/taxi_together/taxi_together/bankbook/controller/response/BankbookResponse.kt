package taxi_together.taxi_together.bankbook.controller.response

import org.springframework.http.ResponseEntity
import taxi_together.taxi_together.bankbook.dto.response.BankbookInfo

object BankbookResponse {
    private const val UPDATE_INFO_SUCCESS = "통장 정보 변경을 성공적으로 완료하였습니다."

    fun infoSuccess(bankbookInfo: BankbookInfo) = ResponseEntity.ok(bankbookInfo)
    fun updateInfoSuccess() = ResponseEntity.ok(UPDATE_INFO_SUCCESS)
}