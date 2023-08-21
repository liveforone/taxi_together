package taxi_together.taxi_together.member.service.command

import taxi_together.taxi_together.member.domain.Member
import taxi_together.taxi_together.member.domain.Role
import taxi_together.taxi_together.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import taxi_together.taxi_together.exception.exception.MemberException
import taxi_together.taxi_together.exception.message.MemberExceptionMessage
import taxi_together.taxi_together.reportState.service.command.RepostStateCommandService

@Service
class CustomUserDetailsService @Autowired constructor(
    private val repostStateCommandService: RepostStateCommandService,
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val reportState = repostStateCommandService.releaseSuspend(email)
        check(reportState.isNotSuspend()) { throw MemberException(MemberExceptionMessage.SUSPEND_MEMBER) }
        val member = memberRepository.findOneByEmail(email)
        return createUserDetails(member)
    }

    private fun createUserDetails(member: Member): UserDetails {
        return when (member.auth) {
            Role.ADMIN -> { createAdmin(member) }
            else -> { createMember(member) }
        }
    }

    private fun createAdmin(member: Member): UserDetails {
        return User.builder()
            .username(member.uuid.toString())
            .password(member.password)
            .roles(Role.ADMIN.name)
            .build()
    }

    private fun createMember(member: Member): UserDetails {
        return User.builder()
            .username(member.uuid.toString())
            .password(member.password)
            .roles(Role.MEMBER.name)
            .build()
    }
}