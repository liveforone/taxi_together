package taxi_together.taxi_together.reportState.dto.response

import taxi_together.taxi_together.reportState.domain.MemberState

data class RepostStateInfo(val memberState: MemberState, val modifiedStateDate: Int, val reportCount: Int)