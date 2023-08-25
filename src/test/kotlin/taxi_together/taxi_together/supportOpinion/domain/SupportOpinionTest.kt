package taxi_together.taxi_together.supportOpinion.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import taxi_together.taxi_together.member.domain.Member

class SupportOpinionTest {
    @Test
    fun createTest() {
        //given
        val email = "test_member@gmail.com"
        val pw = "1234"
        val nickName = "member"
        val writer = Member.create(email, pw, nickName)
        val content = "정지된 회원은 어떻게 정지를 풀 수 있나요?"
        val opinionType = "demand"

        //when
        val supportOpinion = SupportOpinion.create(writer, content, opinionType)

        //then
        Assertions.assertThat(supportOpinion.supportOpinionType).isEqualTo(SupportOpinionType.create(opinionType))
    }
}