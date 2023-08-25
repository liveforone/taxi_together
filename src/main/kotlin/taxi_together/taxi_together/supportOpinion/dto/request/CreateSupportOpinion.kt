package taxi_together.taxi_together.supportOpinion.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateSupportOpinion(
    @field:NotNull(message = "작성자의 외부 식별자를 입력하세요.")
    val writerUUID: UUID?,
    @field:NotBlank(message = "피드백, 문의, 요구사항을 입력하세요.")
    val content: String?,
    @field:NotBlank(message = "지원 타입을 입력하세요.")
    val opinionType: String?
)