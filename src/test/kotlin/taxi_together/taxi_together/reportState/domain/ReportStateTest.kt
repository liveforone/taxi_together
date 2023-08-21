package taxi_together.taxi_together.reportState.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import taxi_together.taxi_together.member.domain.Member

class ReportStateTest {

    @Test
    fun releaseSuspendTest() {
        //given
        val email = "test_member@gmail.com"
        val pw = "1234"
        val nickName = "test_nickName"
        val member = Member.create(email, pw, nickName)
        val reportState = ReportState.create(member)

        //when
        repeat(3) { reportState.addReport() }
        reportState.releaseSuspend()

        //then
        Assertions.assertThat(reportState.memberState).isEqualTo(MemberState.SUSPEND_MONTH)
    }

    @Test
    fun isSuspendTest() {
        //given
        val email = "test_member@gmail.com"
        val pw = "1234"
        val nickName = "test_nickName"
        val member = Member.create(email, pw, nickName)
        val reportState = ReportState.create(member)

        //then
        Assertions.assertThat(reportState.isNotSuspend()).isTrue()
    }

    @Test
    fun addReportTest() {
        //given
        val email = "test_member@gmail.com"
        val pw = "1234"
        val nickName = "test_nickName"
        val member = Member.create(email, pw, nickName)
        val reportState = ReportState.create(member)

        //when
        repeat(3) { reportState.addReport() }

        //then
        Assertions.assertThat(reportState.isNotSuspend()).isFalse()
    }
}