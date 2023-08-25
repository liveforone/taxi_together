package taxi_together.taxi_together.supportOpinion.controller.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import taxi_together.taxi_together.supportOpinion.dto.response.SupportOpinionInfo

object SupportOpinionResponse {
    private const val CREATE_SUCCESS = "지원 의견을 성공적으로 생성하였습니다."
    private const val REMOVE_SUCCESS = "지원 의견을 성공적으로 삭제하였습니다."

    fun detailSuccess(supportOpinion: SupportOpinionInfo) = ResponseEntity.ok(supportOpinion)
    fun allSuccess(supportOpinions: List<SupportOpinionInfo>) = ResponseEntity.ok(supportOpinions)
    fun byOpinionTypeSuccess(supportOpinions: List<SupportOpinionInfo>) = ResponseEntity.ok(supportOpinions)
    fun byWriterSuccess(supportOpinions: List<SupportOpinionInfo>) = ResponseEntity.ok(supportOpinions)
    fun createSuccess() = ResponseEntity.status(HttpStatus.CREATED).body(CREATE_SUCCESS)
    fun removeSuccess() = ResponseEntity.ok(REMOVE_SUCCESS)
}