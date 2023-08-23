package taxi_together.taxi_together.carpool.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import taxi_together.taxi_together.member.domain.Member

class CarpoolTest {

    @Test
    fun calculateCarpool() {
        //given
        val email = "email_test@gmail.com"
        val pw = "1234"
        val nickName = "nickName"
        val member = Member.create(email, pw, nickName)
        val pickupZone = "강남역 1번 출구"
        val month = 8
        val day = 23
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        val carpool = Carpool.create(member, pickupZone, month, day, hour, minute, destination)

        //when
        val totalFare = 4900
        val passengerCount = 2
        carpool.calculateCarpool(totalFare, passengerCount)

        //then
        Assertions.assertThat(carpool.individualFare).isEqualTo(totalFare / (passengerCount + 1))
    }
}