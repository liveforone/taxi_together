package taxi_together.taxi_together.supportOpinion.service.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.member.repository.MemberRepository
import taxi_together.taxi_together.supportOpinion.domain.SupportOpinion
import taxi_together.taxi_together.supportOpinion.dto.request.CreateSupportOpinion
import taxi_together.taxi_together.supportOpinion.dto.request.RemoveSupportOpinion
import taxi_together.taxi_together.supportOpinion.repository.SupportOpinionRepository

@Service
@Transactional
class SupportOpinionCommandService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val supportOpinionRepository: SupportOpinionRepository
) {
    fun createSupportOpinion(createSupportOpinion: CreateSupportOpinion): Long {
        return with(createSupportOpinion) {
            SupportOpinion.create(
                writer = memberRepository.findOneByUUID(writerUUID!!),
                content!!,
                opinionType!!
            ).run { supportOpinionRepository.save(this).id!! }
        }
    }

    fun removeSupportOpinion(removeSupportOpinion: RemoveSupportOpinion) {
        with(removeSupportOpinion) {
            supportOpinionRepository.findOneByIdAndWriter(id!!, writerUUID!!)
                .also { supportOpinionRepository.delete(it) }
        }
    }
}