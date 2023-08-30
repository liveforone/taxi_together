package taxi_together.taxi_together.supportOpinion.dto.request

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class RemoveSupportOpinion(
    @field:NotNull(message = "지원의견의 식별자를 입력하세요.")
    val id: Long?,
    @field:NotNull(message = "작성자의 외부 식별자를 입력하세요.")
    val writerUUID: UUID?,
)
