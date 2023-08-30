package taxi_together.taxi_together.supportAnswer.dto.request

import jakarta.validation.constraints.NotNull
import java.util.*

data class RemoveSupportAnswer(
    @field:NotNull(message = "지원 의견 답변의 식별자를 입력하세요.")
    val id: Long?,
    @field:NotNull(message = "회원의 외부식별자를 입력하세요.")
    val memberUUID: UUID?,
)