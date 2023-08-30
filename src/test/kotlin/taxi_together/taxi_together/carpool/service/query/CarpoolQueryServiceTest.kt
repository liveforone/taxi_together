package taxi_together.taxi_together.carpool.service.query

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.carpool.dto.request.CreateCarpool
import taxi_together.taxi_together.carpool.service.command.CarpoolCommandService
import taxi_together.taxi_together.globalUtil.getDatetimeDigit
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.service.command.MemberCommandService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@SpringBootTest
class CarpoolQueryServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val carpoolCommandService: CarpoolCommandService,
    private val carpoolQueryService: CarpoolQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun getCarpoolByIdTest() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "nickName"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()

        val memberUUID = memberCommandService.login(LoginRequest(email, pw)).uuid
        val pickupLatitude = 37.494461
        val pickupLongitude = 127.029592
        val month = 10
        val day = 23
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        val id = carpoolCommandService.createCarpool(CreateCarpool(memberUUID, pickupLatitude, pickupLongitude, month, day, hour, minute, destination))
        flushAndClear()

        //when
        val carpool = carpoolQueryService.getCarpoolById(id)

        //then
        Assertions.assertThat(carpool.pickupDate).isEqualTo(getDatetimeDigit(LocalDateTime.of(LocalDate.of(LocalDate.now().year, month, day), LocalTime.of(hour, minute))))
    }

    @Test
    @Transactional
    fun getCarpoolsTest() {
        //given
        val email = "signup_test@gmail.com"
        val pw = "1234"
        val nickName = "nickName"
        val bank = "nh"
        val accountNum = "1213456700789"
        val signupRequest = SignupRequest(email, pw, nickName, bank, accountNum)
        memberCommandService.signupMember(signupRequest)
        flushAndClear()

        val memberUUID = memberCommandService.login(LoginRequest(email, pw)).uuid
        val pickupLatitude = 37.494461
        val pickupLongitude = 127.029592
        val month = 10
        val day = 23
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        carpoolCommandService.createCarpool(CreateCarpool(memberUUID, pickupLatitude, pickupLongitude, month, day, hour, minute, destination))
        flushAndClear()

        //when
        val myLatitude = 37.49446
        val myLongitude = 127.029592
        val carpools = carpoolQueryService.getCarpools(myLatitude, myLongitude, null)

        //then
        Assertions.assertThat(carpools).isNotEmpty
    }
}