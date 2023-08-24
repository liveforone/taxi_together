package taxi_together.taxi_together.reportState.repository

import org.springframework.data.jpa.repository.JpaRepository
import taxi_together.taxi_together.reportState.domain.ReportState

interface RepostStateRepository : JpaRepository<ReportState, Long>, RepostStateCustomRepository