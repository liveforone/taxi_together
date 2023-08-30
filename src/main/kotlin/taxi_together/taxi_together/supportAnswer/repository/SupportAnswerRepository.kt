package taxi_together.taxi_together.supportAnswer.repository

import org.springframework.data.jpa.repository.JpaRepository
import taxi_together.taxi_together.supportAnswer.domain.SupportAnswer

interface SupportAnswerRepository : JpaRepository<SupportAnswer, Long>, SupportAnswerCustomRepository