package taxi_together.taxi_together.carpool.dto.request

import jakarta.validation.constraints.NotNull
import java.util.*

data class RemoveCarpool(
    @field:NotNull(message = "카풀의 식별자를 입력하세요.")
    val id: Long?,
    @field:NotNull(message = "회원의 외부 식별자를 입력하세요.")
    val memberUUID: UUID?,
)
