package taxi_together.taxi_together.supportOpinion.repository

import taxi_together.taxi_together.supportOpinion.domain.SupportOpinion
import taxi_together.taxi_together.supportOpinion.dto.response.SupportOpinionInfo
import java.util.UUID

interface SupportOpinionCustomRepository {
    fun findOneById(id: Long): SupportOpinion
    fun findOneByIdAndWriter(id: Long, writerUUID: UUID): SupportOpinion
    fun findOneDtoById(id: Long): SupportOpinionInfo
    fun findAllSupportOpinions(lastId: Long?): List<SupportOpinionInfo>
    fun findSupportOpinionsByType(opinionType: String, lastId: Long?): List<SupportOpinionInfo>
    fun findSupportOpinionsByWriter(writerUUID: UUID, lastId: Long?): List<SupportOpinionInfo>
}