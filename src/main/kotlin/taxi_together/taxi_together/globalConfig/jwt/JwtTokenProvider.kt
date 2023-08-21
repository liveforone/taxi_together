package taxi_together.taxi_together.globalConfig.jwt

import taxi_together.taxi_together.globalConfig.jwt.constant.JwtConstant
import taxi_together.taxi_together.member.dto.response.LoginInfo
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import taxi_together.taxi_together.exception.exception.JwtCustomException
import taxi_together.taxi_together.exception.message.JwtExceptionMessage
import taxi_together.taxi_together.logger
import java.util.*

@Component
class JwtTokenProvider(@Value(JwtConstant.SECRET_KEY_PATH) secretKey: String) {

    private val key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))

    private fun generateAccessToken(authentication: Authentication): String {
        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(
                JwtConstant.CLAIM_NAME,
                authentication.authorities.iterator().next().authority
            )
            .setExpiration(Date(Date().time + JwtConstant.TWO_HOUR_MS))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun generateRefreshToken(): String {
        return Jwts.builder()
            .setExpiration(Date(Date().time + JwtConstant.TWO_HOUR_MS))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateToken(authentication: Authentication): LoginInfo {
        return LoginInfo.create(UUID.fromString(authentication.name), generateAccessToken(authentication), generateRefreshToken())
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)
        val authorities: Collection<GrantedAuthority> =
            claims[JwtConstant.CLAIM_NAME].toString()
                .split(JwtConstant.AUTH_DELIMITER)
                .map { role: String? -> SimpleGrantedAuthority(role) }
        val principal: UserDetails = User(claims.subject, JwtConstant.EMPTY_PW, authorities)
        return UsernamePasswordAuthenticationToken(principal, JwtConstant.CREDENTIAL, authorities)
    }

    fun validateToken(token: String?): Boolean {
        requireNotNull(token) { throw JwtCustomException(JwtExceptionMessage.TOKEN_IS_NULL) }
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: MalformedJwtException) {
            logger().info(JwtExceptionMessage.INVALID_MESSAGE.message)
        } catch (e: ExpiredJwtException) {
            logger().info(JwtExceptionMessage.EXPIRED_MESSAGE.message)
        } catch (e: UnsupportedJwtException) {
            logger().info(JwtExceptionMessage.UNSUPPORTED_MESSAGE.message)
        } catch (e: SecurityException) {
            logger().info(JwtExceptionMessage.INVALID_MESSAGE.message)
        }
        return false
    }

    private fun parseClaims(accessToken: String?): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}