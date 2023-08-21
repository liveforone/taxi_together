package taxi_together.taxi_together.reportState.service.command

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.service.command.MemberCommandService
import taxi_together.taxi_together.reportState.domain.MemberState
import taxi_together.taxi_together.reportState.dto.request.ReportMember
import taxi_together.taxi_together.reportState.service.query.ReportStateQueryService

@SpringBootTest
class RepostStateCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val repostStateCommandService: RepostStateCommandService,
    private val reportStateQueryService: ReportStateQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createRepostState() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "member"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)

        //when
        memberCommandService.signupMember(signupRequest)
        flushAndClear()

        //then
        val loginRequest = LoginRequest(email, pw)
        val memberUUID = memberCommandService.login(loginRequest).uuid
        Assertions.assertThat(reportStateQueryService.getOneByMemberUUID(memberUUID).memberState)
            .isEqualTo(MemberState.NORMAL)
    }

    @Test
    @Transactional
    fun releaseSuspend() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "member"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()
        val memberUUID = memberCommandService.login(LoginRequest(email, pw)).uuid

        //when
        repeat(3) {
            val dtoRequest = ReportMember(memberUUID)
            repostStateCommandService.addRepost(dtoRequest)
            flushAndClear()
        }
        val loginRequest = LoginRequest(email, pw)

        //then
        Assertions.assertThatThrownBy { memberCommandService.login(loginRequest) }
            .isInstanceOf(InternalAuthenticationServiceException::class.java)
    }

    @Test
    @Transactional
    fun addRepost() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "member"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()
        val memberUUID = memberCommandService.login(LoginRequest(email, pw)).uuid

        //when
        repeat(3) {
            val dtoRequest = ReportMember(memberUUID)
            repostStateCommandService.addRepost(dtoRequest)
            flushAndClear()
        }

        //then
        Assertions.assertThat(reportStateQueryService.getOneByMemberUUID(memberUUID).memberState)
            .isEqualTo(MemberState.SUSPEND_MONTH)
    }
}