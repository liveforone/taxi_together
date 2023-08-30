package taxi_together.taxi_together.carpool.dto.update

import jakarta.validation.constraints.NotNull

data class CalculateCarpool(
    @field:NotNull(message = "카풀의 식별자를 입력하세요.")
    val id: Long?,
    @field:NotNull(message = "총 택시비를 입력하세요.")
    val totalFare: Int?
)