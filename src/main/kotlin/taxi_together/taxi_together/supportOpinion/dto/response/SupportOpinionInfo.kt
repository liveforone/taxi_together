package taxi_together.taxi_together.supportOpinion.dto.response

import taxi_together.taxi_together.supportOpinion.domain.SupportOpinionType
import java.util.UUID

data class SupportOpinionInfo(
    val uuid: UUID,
    val content: String,
    val supportOpinionType: SupportOpinionType,
    val createdDatetime: Long
)