package taxi_together.taxi_together.supportOpinion.service.query

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.supportOpinion.repository.SupportOpinionRepository
import java.util.UUID

@Service
@Transactional(readOnly = true)
class SupportOpinionQueryService @Autowired constructor(
    private val supportOpinionRepository: SupportOpinionRepository
) {
    fun getOneByUUID(uuid: UUID) = supportOpinionRepository.findOneDtoByUUID(uuid)
    fun getAllSupportOpinion(lastUUID: UUID?) = supportOpinionRepository.findAllSupportOpinions(lastUUID)
    fun getSupportOpinionsByType(opinionType: String, lastUUID: UUID?) =
        supportOpinionRepository.findSupportOpinionsByType(opinionType, lastUUID)
    fun getSupportOpinionsByWriter(writerUUID: UUID, lastUUID: UUID?) =
        supportOpinionRepository.findSupportOpinionsByWriter(writerUUID, lastUUID)
}