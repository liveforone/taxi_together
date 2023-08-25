package taxi_together.taxi_together.supportOpinion.repository

import taxi_together.taxi_together.supportOpinion.domain.SupportOpinion
import taxi_together.taxi_together.supportOpinion.dto.response.SupportOpinionInfo
import java.util.UUID

interface SupportOpinionCustomRepository {
    fun findOneByUUID(uuid: UUID): SupportOpinion
    fun findOneByUUIDAndWriter(uuid: UUID, writerUUID: UUID): SupportOpinion
    fun findOneDtoByUUID(uuid: UUID): SupportOpinionInfo
    fun findAllSupportOpinions(lastUUID: UUID?): List<SupportOpinionInfo>
    fun findSupportOpinionsByType(opinionType: String, lastUUID: UUID?): List<SupportOpinionInfo>
    fun findSupportOpinionsByWriter(writerUUID: UUID, lastUUID: UUID?): List<SupportOpinionInfo>
}