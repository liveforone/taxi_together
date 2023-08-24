package taxi_together.taxi_together.carpoolApplication.dto.request

import jakarta.validation.constraints.NotNull
import java.util.*

data class CancelCarpoolApplication(
    @field:NotNull(message = "카풀의 외부 식별자를 입력하세요.")
    val carpoolUUID: UUID?,
    @field:NotNull(message = "회원의 외부 식별자를 입력하세요.")
    val memberUUID: UUID?
)
