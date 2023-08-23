package taxi_together.taxi_together.carpool.domain

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import taxi_together.taxi_together.carpool.domain.constant.CarpoolConstant
import taxi_together.taxi_together.converter.CarpoolStateConverter
import taxi_together.taxi_together.globalUtil.DATETIME_TYPE
import taxi_together.taxi_together.globalUtil.UUID_TYPE
import taxi_together.taxi_together.globalUtil.createUUID
import taxi_together.taxi_together.globalUtil.getDatetimeDigit
import taxi_together.taxi_together.member.domain.Member
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Entity
class Carpool private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(columnDefinition = UUID_TYPE, unique = true, nullable = false) val uuid: UUID = createUUID(),
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) @OnDelete(action = OnDeleteAction.CASCADE) val member: Member,
    @Convert(converter = CarpoolStateConverter::class) @Column(
        nullable = false,
        columnDefinition = CarpoolConstant.CARPOOL_STATE_TYPE
    ) var carpoolState: CarpoolState = CarpoolState.UNCOMPLETED,
    @Column(nullable = false) val pickupLatitude: Double,
    @Column(nullable = false) val pickupLongitude: Double,
    @Column(nullable = false, columnDefinition = DATETIME_TYPE) val pickupDate: Long,
    @Column(nullable = false) val destination: String,
    @Column(nullable = false) var individualFare: Int = 0
) {
    companion object {
        private fun createPickupDate(month: Int, day: Int, hour: Int, minute: Int) =
            LocalDateTime.of(LocalDate.of(LocalDate.now().year, month, day), LocalTime.of(hour, minute))

        fun create(member: Member, pickupLatitude: Double, pickupLongitude: Double, month: Int, day: Int, hour: Int, minute: Int, destination: String): Carpool {
            return Carpool(
                member = member,
                pickupLatitude = pickupLatitude,
                pickupLongitude = pickupLongitude,
                pickupDate = getDatetimeDigit(createPickupDate(month, day, hour, minute)),
                destination = destination
            )
        }
    }

    fun calculateCarpool(totalFare: Int, passengerCount: Int) {
        this.carpoolState = CarpoolState.COMPLETED
        this.individualFare = totalFare / (passengerCount + CarpoolConstant.COUNT_OF_REGISTRANT)
    }
}