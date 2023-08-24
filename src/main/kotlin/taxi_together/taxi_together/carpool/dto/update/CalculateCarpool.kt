package taxi_together.taxi_together.carpool.dto.update

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CalculateCarpool(
    @field:NotNull(message = "카풀의 외부식별자를 입력하세요.")
    val uuid: UUID?,
    @field:NotNull(message = "총 택시비를 입력하세요.")
    val totalFare: Int?
)