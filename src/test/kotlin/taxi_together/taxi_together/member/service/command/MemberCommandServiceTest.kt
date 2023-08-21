package taxi_together.taxi_together.member.service.command

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.exception.exception.MemberException
import taxi_together.taxi_together.member.domain.Role
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.dto.request.WithdrawRequest
import taxi_together.taxi_together.member.dto.update.UpdateEmail
import taxi_together.taxi_together.member.service.query.MemberQueryService

@SpringBootTest
class MemberCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val memberQueryService: MemberQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun signupMemberTest() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "nickName"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)

        //when
        memberCommandService.signupMember(signupRequest)
        flushAndClear()

        //then
        val loginRequest = LoginRequest(email, pw)
        val loginInfo = memberCommandService.login(loginRequest)
        Assertions.assertThat(memberQueryService.getMemberByUUID(loginInfo.uuid).auth)
            .isEqualTo(Role.MEMBER)
    }

    @Test
    @Transactional
    fun updateEmailTest() {
        //given
        val email = "email_test@gmail.com"
        val pw = "1234"
        val nickName = "nickName"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()
        val loginRequest = LoginRequest(email, pw)
        val uuid = memberCommandService.login(loginRequest).uuid

        //when
        val newEmail = "updated_email@gmail.com"
        val updateRequest = UpdateEmail(newEmail)
        memberCommandService.updateEmail(updateRequest, uuid)
        flushAndClear()

        //then
        Assertions.assertThat(memberQueryService.getMemberByUUID(uuid).email)
            .isEqualTo(newEmail)
    }

    /*
    * 회원 탈퇴후 해당 회원을 다시 조회하게 되면, MemberException(MEMBER_IS_NULL) 예외가 발생하게 됩니다.
     */
    @Test
    @Transactional
    fun withdrawTest() {
        //given
        val email = "withdraw_test@gmail.com"
        val pw = "1234"
        val nickName = "nickName"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()
        val loginRequest = LoginRequest(email, pw)
        val uuid = memberCommandService.login(loginRequest).uuid

        //when
        val withdrawRequest = WithdrawRequest(pw)
        memberCommandService.withdraw(withdrawRequest, uuid)
        flushAndClear()

        //then
        Assertions.assertThatThrownBy { (memberQueryService.getMemberByUUID(uuid)) }
            .isInstanceOf(MemberException::class.java)
    }
}