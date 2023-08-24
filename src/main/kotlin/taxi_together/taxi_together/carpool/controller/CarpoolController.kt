package taxi_together.taxi_together.carpool.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import taxi_together.taxi_together.carpool.controller.constant.CarpoolControllerLog
import taxi_together.taxi_together.carpool.controller.constant.CarpoolParam
import taxi_together.taxi_together.carpool.controller.constant.CarpoolUrl
import taxi_together.taxi_together.carpool.controller.response.CarpoolResponse
import taxi_together.taxi_together.carpool.dto.request.CreateCarpool
import taxi_together.taxi_together.carpool.dto.request.RemoveCarpool
import taxi_together.taxi_together.carpool.dto.update.CalculateCarpool
import taxi_together.taxi_together.carpool.service.command.CarpoolCommandService
import taxi_together.taxi_together.carpool.service.query.CarpoolQueryService
import taxi_together.taxi_together.globalUtil.validateBinding
import taxi_together.taxi_together.logger
import java.util.UUID

@RestController
class CarpoolController @Autowired constructor(
    private val carpoolQueryService: CarpoolQueryService,
    private val carpoolCommandService: CarpoolCommandService
) {
    @GetMapping(CarpoolUrl.DETAIL)
    fun detail(@PathVariable(CarpoolParam.UUID) uuid: UUID): ResponseEntity<*> {
        val carpool = carpoolQueryService.getCarpoolByUUID(uuid)
        return CarpoolResponse.detailSuccess(carpool)
    }

    @GetMapping(CarpoolUrl.SEARCH)
    fun search(
        @RequestParam(CarpoolParam.CURR_LATITUDE) currLatitude: Double,
        @RequestParam(CarpoolParam.CURR_LONGITUDE) currLongitude: Double,
        @RequestParam(CarpoolParam.LAST_UUID, required = false) lastUUID: UUID?
    ): ResponseEntity<*> {
        val carpools = carpoolQueryService.getCarpools(currLatitude, currLongitude, lastUUID)
        return CarpoolResponse.searchSuccess(carpools)
    }

    @PostMapping(CarpoolUrl.CREATE)
    fun create(
        @RequestBody @Valid createCarpool: CreateCarpool,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        carpoolCommandService.createCarpool(createCarpool)
        logger().info(CarpoolControllerLog.CREATE_SUCCESS.log)

        return CarpoolResponse.createSuccess()
    }

    @PutMapping(CarpoolUrl.CALCULATE)
    fun calculate(
        @RequestBody @Valid calculateCarpool: CalculateCarpool,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        carpoolCommandService.calculateCarpool(calculateCarpool)
        logger().info(CarpoolControllerLog.CALCULATE_SUCCESS.log)

        return CarpoolResponse.calculateSuccess()
    }

    @DeleteMapping(CarpoolUrl.REMOVE)
    fun remove(
        @RequestBody @Valid removeCarpool: RemoveCarpool,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        carpoolCommandService.removeCarpool(removeCarpool)
        logger().info(CarpoolControllerLog.REMOVE_SUCCESS.log)

        return CarpoolResponse.removeSuccess()
    }
}