package taxi_together.taxi_together.supportAnswer.service.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.exception.exception.SupportAnswerException
import taxi_together.taxi_together.exception.message.SupportAnswerExceptionMessage
import taxi_together.taxi_together.member.repository.MemberRepository
import taxi_together.taxi_together.supportAnswer.domain.SupportAnswer
import taxi_together.taxi_together.supportAnswer.dto.request.CreateSupportAnswer
import taxi_together.taxi_together.supportAnswer.dto.request.RemoveSupportAnswer
import taxi_together.taxi_together.supportAnswer.repository.SupportAnswerRepository
import taxi_together.taxi_together.supportOpinion.repository.SupportOpinionRepository

@Service
@Transactional
class SupportAnswerCommandService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val supportOpinionRepository: SupportOpinionRepository,
    private val supportAnswerRepository: SupportAnswerRepository
) {
    fun createSupportAnswer(createSupportAnswer: CreateSupportAnswer) {
        with(createSupportAnswer) {
            require(memberRepository.findOneByUUID(memberUUID!!).isAdmin()) { throw SupportAnswerException(SupportAnswerExceptionMessage.WRITER_IS_NOT_ADMIN) }
            SupportAnswer.create(
                supportOpinion = supportOpinionRepository.findOneById(supportOpinionId!!),
                content!!
            ).also { supportAnswerRepository.save(it) }
        }
    }

    fun removeSupportAnswer(removeSupportAnswer: RemoveSupportAnswer) {
        with(removeSupportAnswer) {
            require(memberRepository.findOneByUUID(memberUUID!!).isAdmin()) { throw SupportAnswerException(SupportAnswerExceptionMessage.WRITER_IS_NOT_ADMIN) }
            supportAnswerRepository.findOneById(id!!).also { supportAnswerRepository.delete(it) }
        }
    }
}