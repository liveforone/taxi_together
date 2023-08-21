package taxi_together.taxi_together.globalConfig.jwt.constant

object JwtConstant {
    const val HEADER = "Authorization"
    const val CLAIM_NAME = "auth"
    const val AUTH_DELIMITER = ","
    const val EMPTY_PW = ""
    const val CREDENTIAL = ""
    const val TWO_HOUR_MS = 7200000
    const val BEARER_TOKEN = "Bearer"
    const val SECRET_KEY_PATH = "\${jwt.secret}"
    const val TOKEN_SUB_INDEX = 7
}