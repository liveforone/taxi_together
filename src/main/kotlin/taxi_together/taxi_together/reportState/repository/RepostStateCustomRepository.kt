package taxi_together.taxi_together.reportState.repository

import taxi_together.taxi_together.reportState.domain.ReportState
import taxi_together.taxi_together.reportState.dto.response.RepostStateInfo
import java.util.UUID

interface RepostStateCustomRepository {
    fun findOneByMemberEmail(email: String): ReportState
    fun findOneByMemberUUID(memberUUID: UUID): ReportState
    fun findOneDtoByMemberUUID(memberUUID: UUID): RepostStateInfo
}