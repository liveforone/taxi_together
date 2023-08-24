package taxi_together.taxi_together.carpool.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateCarpool(
    @field:NotNull(message = "회원의 외부 식별자를 입력하세요.")
    val memberUUID: UUID?,
    @field:NotNull(message = "탑승지의 위도 좌표를 입력하세요.")
    val pickupLatitude: Double?,
    @field:NotNull(message = "탑승지의 경도 좌표를 입력하세요.")
    val pickupLongitude: Double?,
    @field:NotNull(message = "탑승 날짜(월)를 입력하세요.")
    val month: Int?,
    @field:NotNull(message = "탑승 날짜(일)를 입력하세요.")
    val day: Int?,
    @field:NotNull(message = "탑승 시간(시)을 입력하세요.")
    val hour: Int?,
    @field:NotNull(message = "탑승 시간(분)을 입력하세요.")
    val minute: Int?,
    @field:NotBlank(message = "목적지를 입력하세요.")
    val destination: String?
)
