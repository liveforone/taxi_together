package taxi_together.taxi_together.carpool.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import taxi_together.taxi_together.globalUtil.getDatetimeDigit
import taxi_together.taxi_together.member.domain.Member
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CarpoolTest {

    @Test
    fun calculateCarpoolTest() {
        //given
        val email = "test_member@gmail.com"
        val pw = "1234"
        val nickName = "nickName"
        val member = Member.create(email, pw, nickName)
        val pickupLatitude = 37.494461
        val pickupLongitude = 127.029592
        val month = 8
        val day = 23
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        val carpool = Carpool.create(member, pickupLatitude, pickupLongitude, month, day, hour, minute, destination)

        //when
        val totalFare = 4900
        val passengerCount = 2
        carpool.calculateCarpool(totalFare, passengerCount)

        //then
        Assertions.assertThat(carpool.individualFare).isEqualTo(totalFare / (passengerCount + 1))
        Assertions.assertThat(carpool.pickupDate).isEqualTo(getDatetimeDigit(LocalDateTime.of(LocalDate.of(LocalDate.now().year, month, day), LocalTime.of(hour, minute))))
    }
}