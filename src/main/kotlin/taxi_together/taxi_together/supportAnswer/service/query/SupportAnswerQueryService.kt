package taxi_together.taxi_together.supportAnswer.service.query

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.supportAnswer.repository.SupportAnswerRepository

@Service
@Transactional(readOnly = true)
class SupportAnswerQueryService @Autowired constructor(
    private val supportAnswerRepository: SupportAnswerRepository
) {
    fun getSupportAnswerBySupportOpinion(supportOpinionId: Long) =
        supportAnswerRepository.findAnswersBySupportOpinion(supportOpinionId)
}