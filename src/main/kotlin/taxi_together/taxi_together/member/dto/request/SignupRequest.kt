package taxi_together.taxi_together.member.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:NotBlank(message = "이메일을 입력하세요.") val email: String?,
    @field:NotBlank(message = "비밀번호를 입력하세요.") val pw: String?,
    @field:NotBlank(message = "사용할 닉네임을 입력하세요.") val nickName: String?,
    @field:NotBlank(message = "은행을 입력해주세요.") val bank: String?,
    @field:NotBlank(message = "계좌번호를 입력하세요.") @field:Size(max = 20, message = "계좌번호는 20자 이하여야 가능합니다.") val accountNumber: String?
)