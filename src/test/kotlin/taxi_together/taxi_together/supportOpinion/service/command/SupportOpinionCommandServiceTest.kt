package taxi_together.taxi_together.supportOpinion.service.command

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.exception.exception.SupportOpinionException
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.service.command.MemberCommandService
import taxi_together.taxi_together.supportOpinion.domain.SupportOpinionType
import taxi_together.taxi_together.supportOpinion.dto.request.CreateSupportOpinion
import taxi_together.taxi_together.supportOpinion.dto.request.RemoveSupportOpinion
import taxi_together.taxi_together.supportOpinion.service.query.SupportOpinionQueryService

@SpringBootTest
class SupportOpinionCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val supportOpinionCommandService: SupportOpinionCommandService,
    private val supportOpinionQueryService: SupportOpinionQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createSupportOpinion() {
        //given
        val email = "test_member@gmail.com"
        val pw = "1234"
        val nickName = "member"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()
        val writerUUID = memberCommandService.login(LoginRequest(email, pw)).uuid

        //when
        val content = "신고를 몇번 당해야 계정이 정지되나요?"
        val opinionType = "qna"
        val supportUUID = supportOpinionCommandService.createSupportOpinion(CreateSupportOpinion(writerUUID, content, opinionType))
        flushAndClear()

        //then
        Assertions.assertThat(supportOpinionQueryService.getOneByUUID(supportUUID).supportOpinionType)
            .isEqualTo(SupportOpinionType.create(opinionType))
    }

    @Test
    @Transactional
    fun removeSupportOpinion() {
        //given
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
        val supportUUID = supportOpinionCommandService.createSupportOpinion(CreateSupportOpinion(writerUUID, content, opinionType))
        flushAndClear()

        //when
        supportOpinionCommandService.removeSupportOpinion(RemoveSupportOpinion(supportUUID, writerUUID))
        flushAndClear()

        //then
        Assertions.assertThatThrownBy { supportOpinionQueryService.getOneByUUID(supportUUID) }
            .isInstanceOf(SupportOpinionException::class.java)
    }
}