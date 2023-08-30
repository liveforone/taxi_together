package taxi_together.taxi_together.supportOpinion.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import taxi_together.taxi_together.globalUtil.validateBinding
import taxi_together.taxi_together.logger
import taxi_together.taxi_together.supportOpinion.controller.constant.SupportOpinionControllerLog
import taxi_together.taxi_together.supportOpinion.controller.constant.SupportOpinionParam
import taxi_together.taxi_together.supportOpinion.controller.constant.SupportOpinionUrl
import taxi_together.taxi_together.supportOpinion.controller.response.SupportOpinionResponse
import taxi_together.taxi_together.supportOpinion.dto.request.CreateSupportOpinion
import taxi_together.taxi_together.supportOpinion.dto.request.RemoveSupportOpinion
import taxi_together.taxi_together.supportOpinion.service.command.SupportOpinionCommandService
import taxi_together.taxi_together.supportOpinion.service.query.SupportOpinionQueryService
import java.util.UUID

@RestController
class SupportOpinionController @Autowired constructor(
    private val supportOpinionQueryService: SupportOpinionQueryService,
    private val supportOpinionCommandService: SupportOpinionCommandService
) {
    @GetMapping(SupportOpinionUrl.DETAIL)
    fun detail(@PathVariable(SupportOpinionParam.ID) id: Long): ResponseEntity<*> {
        val supportOpinion = supportOpinionQueryService.getOneById(id)
        return SupportOpinionResponse.detailSuccess(supportOpinion)
    }

    @GetMapping(SupportOpinionUrl.ALL)
    fun all(@RequestParam(SupportOpinionParam.LAST_ID, required = false) lastId: Long?): ResponseEntity<*> {
        val supports = supportOpinionQueryService.getAllSupportOpinion(lastId)
        return SupportOpinionResponse.allSuccess(supports)
    }

    @GetMapping(SupportOpinionUrl.BY_OPINION_TYPE)
    fun byOpinionType(
        @PathVariable(SupportOpinionParam.OPINION_TYPE) opinionType: String,
        @RequestParam(SupportOpinionParam.LAST_ID, required = false) lastId: Long?
    ): ResponseEntity<*> {
        val supports = supportOpinionQueryService.getSupportOpinionsByType(opinionType, lastId)
        return SupportOpinionResponse.byOpinionTypeSuccess(supports)
    }

    @GetMapping(SupportOpinionUrl.BY_WRITER)
    fun byWriter(
        @PathVariable(SupportOpinionParam.WRITER_UUID) writerUUID: UUID,
        @RequestParam(SupportOpinionParam.LAST_ID, required = false) lastId: Long?
    ): ResponseEntity<*> {
        val supports = supportOpinionQueryService.getSupportOpinionsByWriter(writerUUID, lastId)
        return SupportOpinionResponse.byWriterSuccess(supports)
    }

    @PostMapping(SupportOpinionUrl.CREATE)
    fun create(
        @RequestBody @Valid createSupportOpinion: CreateSupportOpinion,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        supportOpinionCommandService.createSupportOpinion(createSupportOpinion)
        logger().info(SupportOpinionControllerLog.CREATE_SUCCESS.log)

        return SupportOpinionResponse.createSuccess()
    }

    @DeleteMapping(SupportOpinionUrl.REMOVE)
    fun remove(
        @RequestBody @Valid removeSupportOpinion: RemoveSupportOpinion,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        supportOpinionCommandService.removeSupportOpinion(removeSupportOpinion)
        logger().info(SupportOpinionControllerLog.REMOVE_SUCCESS.log)

        return SupportOpinionResponse.removeSuccess()
    }
}