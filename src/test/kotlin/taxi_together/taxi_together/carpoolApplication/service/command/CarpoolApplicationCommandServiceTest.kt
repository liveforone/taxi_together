package taxi_together.taxi_together.carpoolApplication.service.command

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.carpool.dto.request.CreateCarpool
import taxi_together.taxi_together.carpool.service.command.CarpoolCommandService
import taxi_together.taxi_together.carpoolApplication.dto.request.CancelCarpoolApplication
import taxi_together.taxi_together.carpoolApplication.dto.request.CreateCarpoolApplication
import taxi_together.taxi_together.carpoolApplication.service.query.CarpoolApplicationQueryService
import taxi_together.taxi_together.member.dto.request.LoginRequest
import taxi_together.taxi_together.member.dto.request.SignupRequest
import taxi_together.taxi_together.member.service.command.MemberCommandService

@SpringBootTest
class CarpoolApplicationCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val carpoolCommandService: CarpoolCommandService,
    private val carpoolApplicationCommandService: CarpoolApplicationCommandService,
    private val carpoolApplicationQueryService: CarpoolApplicationQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createCarpoolApplicationTest() {
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
        val day = 30
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        val carpoolUUID = carpoolCommandService.createCarpool(CreateCarpool(ownerOfCarpoolUUID, pickupLatitude, pickupLongitude, month, day, hour, minute, destination))
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

        //when
        carpoolApplicationCommandService.createCarpoolApplication(CreateCarpoolApplication(carpoolUUID, memberUUID))
        flushAndClear()

        //then
        Assertions.assertThat(carpoolApplicationQueryService.getCarpoolApplicationsByMemberUUID(memberUUID))
            .isNotEmpty
    }

    @Test
    @Transactional
    fun cancelCarpoolApplicationTest() {
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
        val day = 30
        val hour = 18
        val minute = 17
        val destination = "잠실역 3번 출구"
        val carpoolUUID = carpoolCommandService.createCarpool(CreateCarpool(ownerOfCarpoolUUID, pickupLatitude, pickupLongitude, month, day, hour, minute, destination))
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
        carpoolApplicationCommandService.createCarpoolApplication(CreateCarpoolApplication(carpoolUUID, memberUUID))
        flushAndClear()

        //when
        carpoolApplicationCommandService.cancelCarpoolApplication(CancelCarpoolApplication(carpoolUUID, memberUUID))
        flushAndClear()

        //then
        Assertions.assertThat(carpoolApplicationQueryService.getCarpoolApplicationsByMemberUUID(memberUUID))
            .isEmpty()
    }
}