package taxi_together.taxi_together.supportAnswer.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateSupportAnswer(
    @field:NotNull(message = "회원의 외부식별자를 입력하세요.")
    val memberUUID: UUID?,
    @field:NotNull(message = "지원 의견의 외부식별자를 입력하세요.")
    val supportOpinionId: Long?,
    @field:NotBlank(message = "지원 의견 답변을 입력하세요.")
    val content: String?
)