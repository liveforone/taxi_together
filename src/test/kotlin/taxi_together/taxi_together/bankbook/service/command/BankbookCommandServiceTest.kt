package taxi_together.taxi_together.bankbook.service.command

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.bankbook.domain.Bank
import taxi_together.taxi_together.bankbook.dto.update.UpdateBankbook
import taxi_together.taxi_together.bankbook.service.query.BankbookQueryService
import taxi_together.taxi_together.exception.exception.BankbookException
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.dto.request.WithdrawRequest
import taxi_together.taxi_together.member.service.command.MemberCommandService

@SpringBootTest
class BankbookCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val bankbookCommandService: BankbookCommandService,
    private val bankbookQueryService: BankbookQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createBankbook() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "nickname"
        val bank = "nh"
        val accountNum = "1213456700789"

        //when
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()

        //then
        val loginRequest = LoginRequest(email, pw)
        val memberUUID = memberCommandService.login(loginRequest).uuid
        Assertions.assertThat(bankbookQueryService.getBankbookByMember(memberUUID).bank)
            .isEqualTo(Bank.NH)
    }

    @Test
    @Transactional
    fun updateBankbook() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "nickname"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()

        //when
        val updatedBank = "shinhan"
        val updatedAccountNum = "3124487300613"
        val memberUUID = memberCommandService.login(LoginRequest(email, pw)).uuid
        val updateRequest = UpdateBankbook(memberUUID, updatedBank, updatedAccountNum)
        bankbookCommandService.updateBankbook(updateRequest)
        flushAndClear()

        //then
        val bankbook = bankbookQueryService.getBankbookByMember(memberUUID)
        Assertions.assertThat(bankbook.bank).isEqualTo(Bank.SHINHAN)
        Assertions.assertThat(bankbook.accountNumber).isEqualTo(updatedAccountNum)
    }

    @Test
    @Transactional
    fun onDeleteBankbookTest() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "nickname"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()

        //when
        val withdrawRequest = WithdrawRequest(pw)
        val memberUUID = memberCommandService.login(LoginRequest(email, pw)).uuid
        memberCommandService.withdraw(withdrawRequest, memberUUID)
        flushAndClear()

        //then
        Assertions.assertThatThrownBy {
            bankbookQueryService.getBankbookByMember(memberUUID)
        }.isInstanceOf(BankbookException::class.java)
    }
}