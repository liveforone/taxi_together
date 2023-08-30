package taxi_together.taxi_together.supportAnswer.controller.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import taxi_together.taxi_together.supportAnswer.dto.response.SupportAnswerInfo

object SupportAnswerResponse {
    private const val CREATE_SUCCESS = "지원의견 답변을 성공적으로 등록했습니다."
    private const val REMOVE_SUCCESS = "지원의견 답변을 성공적으로 삭제했습니다."

    fun bySupportOpinionSuccess(supportAnswerInfo: List<SupportAnswerInfo>) = ResponseEntity.ok(supportAnswerInfo)
    fun createSuccess() = ResponseEntity.status(HttpStatus.CREATED).body(CREATE_SUCCESS)
    fun removeSuccess() = ResponseEntity.ok(REMOVE_SUCCESS)
}