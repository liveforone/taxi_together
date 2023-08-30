package taxi_together.taxi_together.carpool.service.command

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.carpool.dto.request.CreateCarpool
import taxi_together.taxi_together.carpool.dto.request.RemoveCarpool
import taxi_together.taxi_together.carpool.dto.update.CalculateCarpool
import taxi_together.taxi_together.carpool.service.query.CarpoolQueryService
import taxi_together.taxi_together.carpoolApplication.dto.request.CreateCarpoolApplication
import taxi_together.taxi_together.carpoolApplication.service.command.CarpoolApplicationCommandService
import taxi_together.taxi_together.exception.exception.CarpoolException
import taxi_together.taxi_together.globalUtil.getDatetimeDigit
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.service.command.MemberCommandService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@SpringBootTest
class CarpoolCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val carpoolCommandService: CarpoolCommandService,
    private val carpoolQueryService: CarpoolQueryService,
    private val carpoolApplicationCommandService: CarpoolApplicationCommandService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createCarpoolTest() {
        //given
        val ownerEmail = "owner_of_carpool@gmail.com"
        val ownerPw = "1234"
        val ownerNickName = "owner"
        val ownerBank = "nh"
        val ownerAccountNum = "1213456700789"
        val ownerSignupRequest = SignupRequest(ownerEmail, ownerPw, ownerNickName, ownerBank, ownerAccountNum)
        memberCommandService.signupMember(ownerSignupRequest)
        flushAndClear()

        //when
        val ownerOfCarpoolUUID = memberCommandService.login(LoginRequest(ownerEmail, ownerPw)).uuid
        val pickupLatitude = 37.494461
        val pickupLongitude = 127.029592
        val month = 8
        val day = 23
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        val carpoolId = carpoolCommandService.createCarpool(CreateCarpool(ownerOfCarpoolUUID, pickupLatitude, pickupLongitude, month, day, hour, minute, destination))
        flushAndClear()

        //then
        Assertions.assertThat(carpoolQueryService.getCarpoolById(carpoolId).pickupDate)
            .isEqualTo(getDatetimeDigit(LocalDateTime.of(LocalDate.of(LocalDate.now().year, month, day), LocalTime.of(hour, minute))))
    }

    //==날짜 + 시간에 민감하므로, 테스트시마다 카풀 시간을 미래시간으로 변경하고 테스트 진행해야한다.
    @Test
    @Transactional
    fun calculateCarpoolTest() {
        //given
        val ownerEmail = "owner_of_carpool@gmail.com"
        val ownerPw = "1234"
        val ownerNickName = "owner"
        val ownerBank = "nh"
        val ownerAccountNum = "1213456700789"
        val ownerSignupRequest = SignupRequest(ownerEmail, ownerPw, ownerNickName, ownerBank, ownerAccountNum)
        memberCommandService.signupMember(ownerSignupRequest)
        flushAndClear()

        val ownerOfCarpoolUUID = memberCommandService.login(LoginRequest(ownerEmail, ownerPw)).uuid
        val pickupLatitude = 37.494461
        val pickupLongitude = 127.029592
        val month = 10
        val day = 23
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        val carpoolId = carpoolCommandService.createCarpool(CreateCarpool(ownerOfCarpoolUUID, pickupLatitude, pickupLongitude, month, day, hour, minute, destination))
        flushAndClear()

        val memberEmail = "test_member@gmail.com"
        val memberPw = "1234"
        val memberNickName = "member"
        val memberBank = "shinhan"
        val memberAccountNum = "341551879931"
        val memberSignupRequest = SignupRequest(memberEmail, memberPw, memberNickName, memberBank, memberAccountNum)
        memberCommandService.signupMember(memberSignupRequest)
        flushAndClear()

        val memberUUID = memberCommandService.login(LoginRequest(memberEmail, memberPw)).uuid
        carpoolApplicationCommandService.createCarpoolApplication(CreateCarpoolApplication(carpoolId, memberUUID))
        flushAndClear()

        //when
        val totalFare = 4900
        carpoolCommandService.calculateCarpool(CalculateCarpool(carpoolId, totalFare))
        flushAndClear()

        //then
        Assertions.assertThat(carpoolQueryService.getCarpoolById(carpoolId).individualFare)
            .isEqualTo(totalFare / 2)
    }

    @Test
    @Transactional
    fun removeCarpoolTest() {
        //given
        val ownerEmail = "owner_of_carpool@gmail.com"
        val ownerPw = "1234"
        val ownerNickName = "owner"
        val ownerBank = "nh"
        val ownerAccountNum = "1213456700789"
        val ownerSignupRequest = SignupRequest(ownerEmail, ownerPw, ownerNickName, ownerBank, ownerAccountNum)
        memberCommandService.signupMember(ownerSignupRequest)
        flushAndClear()

        val ownerOfCarpoolUUID = memberCommandService.login(LoginRequest(ownerEmail, ownerPw)).uuid
        val pickupLatitude = 37.494461
        val pickupLongitude = 127.029592
        val month = 8
        val day = 23
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        val carpoolId = carpoolCommandService.createCarpool(CreateCarpool(ownerOfCarpoolUUID, pickupLatitude, pickupLongitude, month, day, hour, minute, destination))
        flushAndClear()

        //when
        carpoolCommandService.removeCarpool(RemoveCarpool(carpoolId, ownerOfCarpoolUUID))
        flushAndClear()

        //then
        Assertions.assertThatThrownBy { carpoolQueryService.getCarpoolById(carpoolId) }
            .isInstanceOf(CarpoolException::class.java)
    }
}