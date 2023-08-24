package taxi_together.taxi_together.carpoolApplication.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import taxi_together.taxi_together.carpoolApplication.controller.constant.CarpoolApplicationControllerLog
import taxi_together.taxi_together.carpoolApplication.controller.constant.CarpoolApplicationParam
import taxi_together.taxi_together.carpoolApplication.controller.constant.CarpoolApplicationUrl
import taxi_together.taxi_together.carpoolApplication.controller.response.CarpoolApplicationResponse
import taxi_together.taxi_together.carpoolApplication.dto.request.CancelCarpoolApplication
import taxi_together.taxi_together.carpoolApplication.dto.request.CreateCarpoolApplication
import taxi_together.taxi_together.carpoolApplication.service.command.CarpoolApplicationCommandService
import taxi_together.taxi_together.carpoolApplication.service.query.CarpoolApplicationQueryService
import taxi_together.taxi_together.globalUtil.validateBinding
import taxi_together.taxi_together.logger
import java.util.UUID

@RestController
class CarpoolApplicationController @Autowired constructor(
    private val carpoolApplicationQueryService: CarpoolApplicationQueryService,
    private val carpoolApplicationCommandService: CarpoolApplicationCommandService
) {
    @GetMapping(CarpoolApplicationUrl.CARPOOL_APP_BELONG_CARPOOL)
    fun belongCarpool(@PathVariable(CarpoolApplicationParam.CARPOOL_UUID) carpoolUUID: UUID): ResponseEntity<*> {
        val carpoolApps = carpoolApplicationQueryService.getCarpoolApplicationsByCarpoolUUID(carpoolUUID)
        return CarpoolApplicationResponse.belongCarpoolSuccess(carpoolApps)
    }

    @GetMapping(CarpoolApplicationUrl.CARPOOL_APP_BELONG_MEMBER)
    fun belongMember(@PathVariable(CarpoolApplicationParam.MEMBER_UUID) memberUUID: UUID): ResponseEntity<*> {
        val carpoolApps = carpoolApplicationQueryService.getCarpoolApplicationsByMemberUUID(memberUUID)
        return CarpoolApplicationResponse.belongMemberSuccess(carpoolApps)
    }

    @PostMapping(CarpoolApplicationUrl.CREATE_CARPOOL_APP)
    fun create(
        @RequestBody @Valid createCarpoolApplication: CreateCarpoolApplication,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        carpoolApplicationCommandService.createCarpoolApplication(createCarpoolApplication)
        logger().info(CarpoolApplicationControllerLog.CREATE_SUCCESS.log)

        return CarpoolApplicationResponse.createSuccess()
    }

    @DeleteMapping(CarpoolApplicationUrl.CANCEL_CARPOOL_APP)
    fun cancel(
        @RequestBody @Valid cancelCarpoolApplication: CancelCarpoolApplication,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        carpoolApplicationCommandService.cancelCarpoolApplication(cancelCarpoolApplication)
        logger().info(CarpoolApplicationControllerLog.CANCEL_SUCCESS.log)

        return CarpoolApplicationResponse.cancelSuccess()
    }
}