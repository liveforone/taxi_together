package taxi_together.taxi_together.supportAnswer.repository

import taxi_together.taxi_together.supportAnswer.domain.SupportAnswer
import taxi_together.taxi_together.supportAnswer.dto.response.SupportAnswerInfo

interface SupportAnswerCustomRepository {
    fun findOneById(id: Long): SupportAnswer
    fun findAnswersBySupportOpinion(supportOpinionId: Long): List<SupportAnswerInfo>
}