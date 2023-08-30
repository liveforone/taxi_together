package taxi_together.taxi_together.supportAnswer.service.command

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.service.command.MemberCommandService
import taxi_together.taxi_together.supportAnswer.dto.request.CreateSupportAnswer
import taxi_together.taxi_together.supportAnswer.dto.request.RemoveSupportAnswer
import taxi_together.taxi_together.supportAnswer.service.query.SupportAnswerQueryService
import taxi_together.taxi_together.supportOpinion.dto.request.CreateSupportOpinion
import taxi_together.taxi_together.supportOpinion.service.command.SupportOpinionCommandService
import java.util.UUID

@SpringBootTest
class SupportAnswerCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val supportOpinionCommandService: SupportOpinionCommandService,
    private val supportAnswerCommandService: SupportAnswerCommandService,
    private val supportAnswerQueryService: SupportAnswerQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun createSupportOpinion(): Long {
        val email = "test_member@gmail.com"
        val pw = "1234"
        val nickName = "member"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()
        val writerUUID = memberCommandService.login(LoginRequest(email, pw)).uuid

        val content = "신고를 몇번 당해야 계정이 정지되나요?"
        val opinionType = "qna"
        val supportId = supportOpinionCommandService.createSupportOpinion(CreateSupportOpinion(writerUUID, content, opinionType))
        flushAndClear()
        return supportId
    }

    private fun createAdmin(): UUID {
        val email = "admin_taxi_together@gmail.com"
        val pw = "1234"
        val nickName = "admin"
        val bank = "nh"
        val accountNum = "3521199004502"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()
        return memberCommandService.login(LoginRequest(email, pw)).uuid
    }

    @Test
    @Transactional
    fun createSupportAnswerTest() {
        //given
        val adminUUID = createAdmin()
        val supportOpinionId = createSupportOpinion()
        val content = "9번의 신고를 당하면 계정이 영구 정지됩니다."

        //when
        supportAnswerCommandService.createSupportAnswer(CreateSupportAnswer(adminUUID, supportOpinionId, content))
        flushAndClear()

        //then
        Assertions.assertThat(supportAnswerQueryService.getSupportAnswerBySupportOpinion(supportOpinionId)[0].content)
            .isEqualTo(content)
    }

    @Test
    @Transactional
    fun removeSupportAnswerTest() {
        //given
        val adminUUID = createAdmin()
        val supportOpinionId = createSupportOpinion()
        val content = "9번의 신고를 당하면 계정이 영구 정지됩니다."
        supportAnswerCommandService.createSupportAnswer(CreateSupportAnswer(adminUUID, supportOpinionId, content))
        flushAndClear()

        //when
        val id = supportAnswerQueryService.getSupportAnswerBySupportOpinion(supportOpinionId)[0].id
        supportAnswerCommandService.removeSupportAnswer(RemoveSupportAnswer(id, adminUUID))
        flushAndClear()

        //then
        Assertions.assertThat(supportAnswerQueryService.getSupportAnswerBySupportOpinion(supportOpinionId))
            .isEmpty()
    }
}