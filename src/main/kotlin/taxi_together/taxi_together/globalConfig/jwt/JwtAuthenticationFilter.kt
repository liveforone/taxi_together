package taxi_together.taxi_together.globalConfig.jwt

import taxi_together.taxi_together.globalConfig.jwt.constant.JwtConstant
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter @Autowired constructor(
    private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        resolveToken(request as HttpServletRequest)?.let {
            if (jwtTokenProvider.validateToken(it)) {
                val authentication = jwtTokenProvider.getAuthentication(it)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        chain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(JwtConstant.HEADER)
            ?.takeIf { it.startsWith(JwtConstant.BEARER_TOKEN) }
            ?.substring(JwtConstant.TOKEN_SUB_INDEX)
    }
}