package taxi_together.taxi_together.exception.message

enum class JwtExceptionMessage(val status: Int, val message: String) {
    TOKEN_IS_NULL(404, "Token Is Null"),
    INVALID_MESSAGE(403, "Invalid JWT Token"),
    EXPIRED_MESSAGE(400, "Expired JWT Token"),
    UNSUPPORTED_MESSAGE(403, "Unsupported JWT Token"),
    EMPTY_CLAIMS(404, "JWT claims string is empty.")
}