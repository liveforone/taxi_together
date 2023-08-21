package taxi_together.taxi_together.member.dto.response

import taxi_together.taxi_together.member.domain.Role
import java.util.*

data class MemberInfo(
    val uuid: UUID,
    val auth: Role,
    val email: String,
    val nickName: String
)