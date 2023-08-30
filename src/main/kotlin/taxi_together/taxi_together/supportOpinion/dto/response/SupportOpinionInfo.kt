package taxi_together.taxi_together.supportOpinion.dto.response

import taxi_together.taxi_together.supportOpinion.domain.SupportOpinionType

data class SupportOpinionInfo(
    val id: Long,
    val content: String,
    val supportOpinionType: SupportOpinionType,
    val createdDatetime: Long
)