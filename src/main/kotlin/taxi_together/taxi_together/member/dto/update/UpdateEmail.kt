package taxi_together.taxi_together.member.dto.update

import jakarta.validation.constraints.NotBlank

data class UpdateEmail(@field:NotBlank(message = "변경할 이메일을 입력하세요.") val newEmail: String?)