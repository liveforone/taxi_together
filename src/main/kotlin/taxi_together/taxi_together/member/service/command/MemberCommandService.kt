package taxi_together.taxi_together.member.service.command

import taxi_together.taxi_together.globalConfig.jwt.JwtTokenProvider
import taxi_together.taxi_together.member.dto.response.LoginInfo
import taxi_together.taxi_together.member.domain.Member
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.dto.request.WithdrawRequest
import taxi_together.taxi_together.member.dto.update.UpdateEmail
import taxi_together.taxi_together.member.dto.update.UpdatePassword
import taxi_together.taxi_together.member.repository.MemberRepository
import taxi_together.taxi_together.member.service.validator.MemberServiceValidator
import taxi_together.taxi_together.reportState.service.command.RepostStateCommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.bankbook.service.command.BankbookCommandService
import taxi_together.taxi_together.exception.exception.MemberException
import taxi_together.taxi_together.exception.message.MemberExceptionMessage
import taxi_together.taxi_together.globalUtil.isMatchPassword
import java.util.*

@Service
@Transactional
class MemberCommandService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val repostStateCommandService: RepostStateCommandService,
    private val bankbookCommandService: BankbookCommandService,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberServiceValidator: MemberServiceValidator
) {

    fun signupMember(signupRequest: SignupRequest) {
        with(signupRequest) {
            memberServiceValidator.validateDuplicateEmail(email!!)
            Member.create(email, pw!!, nickName!!).also {
                memberRepository.save(it)
                repostStateCommandService.createRepostState(it)
                bankbookCommandService.createBankbook(it, bank!!, accountNumber!!)
            }
        }
    }

    fun login(loginRequest: LoginRequest): LoginInfo {
        val authentication: Authentication = authenticationManagerBuilder
            .`object`
            .authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.pw))

        return jwtTokenProvider.generateToken(authentication)
    }

    fun updateEmail(updateEmail: UpdateEmail, uuid: UUID) {
        memberServiceValidator.validateDuplicateEmail(updateEmail.newEmail!!)
        memberRepository.findOneByUUID(uuid).also { it.updateEmail(updateEmail.newEmail) }
    }

    fun updatePassword(updatePassword: UpdatePassword, uuid: UUID) {
        with(updatePassword) {
            memberRepository.findOneByUUID(uuid).also { it.updatePw(newPassword!!, oldPassword!!) }
        }
    }

    fun withdraw(withdrawRequest: WithdrawRequest, uuid: UUID) {
        memberRepository.findOneByUUID(uuid)
            .takeIf { isMatchPassword(withdrawRequest.pw!!, it.pw) }
            ?.also { memberRepository.delete(it) }
            ?: throw MemberException(MemberExceptionMessage.WRONG_PASSWORD)
    }
}