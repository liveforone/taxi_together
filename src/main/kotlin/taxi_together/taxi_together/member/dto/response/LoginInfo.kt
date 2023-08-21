package taxi_together.taxi_together.member.dto.response

import java.util.UUID

class LoginInfo private constructor(
    val uuid: UUID,
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun create(uuid: UUID, accessToken:String, refreshToken:String): LoginInfo {
            return LoginInfo(uuid, accessToken, refreshToken)
        }
    }
}