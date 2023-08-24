package taxi_together.taxi_together.carpoolApplication.domain

import jakarta.persistence.*
import taxi_together.taxi_together.carpoolApplication.domain.constant.CarpoolApplicationConstant
import taxi_together.taxi_together.globalUtil.getCurrentTimestamp
import java.util.UUID

@Entity
@IdClass(CarpoolApplicationPk::class)
class CarpoolApplication private constructor(
    @Id @Column(name = CarpoolApplicationConstant.CARPOOL_UUID) val carpoolUUID: UUID,
    @Id @Column(name = CarpoolApplicationConstant.MEMBER_UUID) val memberUUID: UUID,
    @Column(updatable = false) val timestamp: Int = getCurrentTimestamp()
) {
    companion object {
        fun create(carpoolUUID: UUID, memberUUID: UUID) = CarpoolApplication(carpoolUUID, memberUUID)
    }
}