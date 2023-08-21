package taxi_together.taxi_together.member.controller

import taxi_together.taxi_together.member.controller.constant.MemberControllerConstant
import taxi_together.taxi_together.member.controller.constant.MemberControllerLog
import taxi_together.taxi_together.member.controller.constant.MemberParam
import taxi_together.taxi_together.member.controller.constant.MemberUrl
import taxi_together.taxi_together.member.controller.response.MemberResponse
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.dto.request.WithdrawRequest
import taxi_together.taxi_together.member.dto.update.UpdateEmail
import taxi_together.taxi_together.member.dto.update.UpdatePassword
import taxi_together.taxi_together.member.service.command.MemberCommandService
import taxi_together.taxi_together.member.service.query.MemberQueryService
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import taxi_together.taxi_together.globalUtil.validateBinding
import taxi_together.taxi_together.logger
import java.security.Principal
import java.util.*

@RestController
class MemberController @Autowired constructor(
    private val memberQueryService: MemberQueryService,
    private val memberCommandService: MemberCommandService
) {

    @GetMapping(MemberUrl.INFO)
    fun memberInfo(principal: Principal): ResponseEntity<*> {
        val member = memberQueryService.getMemberByUUID(uuid = UUID.fromString(principal.name))
        return MemberResponse.infoSuccess(member)
    }

    @PostMapping(MemberUrl.SIGNUP_MEMBER)
    fun signupMember(
        @RequestBody @Valid signupRequest: SignupRequest,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.signupMember(signupRequest)
        logger().info(MemberControllerLog.SIGNUP_SUCCESS.log)

        return MemberResponse.signupSuccess()
    }

    @PostMapping(MemberUrl.LOGIN)
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        val tokenInfo = memberCommandService.login(loginRequest)
        response.apply {
            addHeader(MemberControllerConstant.ACCESS_TOKEN, tokenInfo.accessToken)
            addHeader(MemberControllerConstant.REFRESH_TOKEN, tokenInfo.refreshToken)
            addHeader(MemberControllerConstant.MEMBER_UUID, tokenInfo.uuid.toString())
        }
        logger().info(MemberControllerLog.LOGIN_SUCCESS.log)

        return MemberResponse.loginSuccess()
    }

    @PutMapping(MemberUrl.UPDATE_EMAIL)
    fun updateEmail(
        @PathVariable(MemberParam.UUID) uuid: UUID,
        @RequestBody @Valid updateEmail: UpdateEmail,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.updateEmail(updateEmail, uuid)
        logger().info(MemberControllerLog.UPDATE_EMAIL_SUCCESS.log)

        return MemberResponse.updateEmailSuccess()
    }

    @PutMapping(MemberUrl.UPDATE_PASSWORD)
    fun updatePassword(
        @PathVariable(MemberParam.UUID) uuid: UUID,
        @RequestBody @Valid updatePassword: UpdatePassword,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.updatePassword(updatePassword, uuid)
        logger().info(MemberControllerLog.UPDATE_PW_SUCCESS.log)

        return MemberResponse.updatePwSuccess()
    }

    @DeleteMapping(MemberUrl.WITHDRAW)
    fun withdraw(
        @PathVariable(MemberParam.UUID) uuid: UUID,
        @RequestBody @Valid withdrawRequest: WithdrawRequest,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.withdraw(withdrawRequest, uuid)
        logger().info(MemberControllerLog.WITHDRAW_SUCCESS.log)

        return MemberResponse.withdrawSuccess()
    }
}