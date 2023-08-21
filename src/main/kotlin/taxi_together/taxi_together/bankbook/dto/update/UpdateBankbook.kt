package taxi_together.taxi_together.bankbook.dto.update

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class UpdateBankbook(
    @field:NotNull(message = "회원의 외부 식별자를 입력하세요.") val memberUUID: UUID?,
    @field:NotBlank(message = "은행을 입력해주세요.") val bank: String?,
    @field:NotBlank(message = "계좌번호를 입력하세요.") @field:Size(max = 20, message = "계좌번호는 20자 이하여야 가능합니다.") val accountNumber: String?
)