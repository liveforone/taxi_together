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
    fun getOneById(id: Long) = supportOpinionRepository.findOneDtoById(id)
    fun getAllSupportOpinion(lastId: Long?) = supportOpinionRepository.findAllSupportOpinions(lastId)
    fun getSupportOpinionsByType(opinionType: String, lastId: Long?) =
        supportOpinionRepository.findSupportOpinionsByType(opinionType, lastId)
    fun getSupportOpinionsByWriter(writerUUID: UUID, lastId: Long?) =
        supportOpinionRepository.findSupportOpinionsByWriter(writerUUID, lastId)
}