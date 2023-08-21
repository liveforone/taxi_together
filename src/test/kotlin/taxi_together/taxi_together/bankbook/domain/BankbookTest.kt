package taxi_together.taxi_together.bankbook.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import taxi_together.taxi_together.member.domain.Member

class BankbookTest {

    @Test
    fun updateBankbookTest() {
        //given
        val email = "test_member@gmail.com"
        val pw = "1234"
        val nickName = "test_nickName"
        val member = Member.create(email, pw, nickName)
        val bank = "nh"
        val accountNum = "1112222333344"
        val bankbook = Bankbook.create(member, bank, accountNum)

        //when
        val updatedAccountNum = "3531199100100"
        bankbook.updateBankbook(bank, updatedAccountNum)

        //then
        Assertions.assertThat(bankbook.accountNumber).isEqualTo(updatedAccountNum)
    }
}