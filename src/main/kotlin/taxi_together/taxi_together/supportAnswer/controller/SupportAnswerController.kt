package taxi_together.taxi_together.supportAnswer.controller

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
import taxi_together.taxi_together.globalUtil.validateBinding
import taxi_together.taxi_together.logger
import taxi_together.taxi_together.supportAnswer.controller.constant.SupportAnswerControllerLog
import taxi_together.taxi_together.supportAnswer.controller.constant.SupportAnswerParam
import taxi_together.taxi_together.supportAnswer.controller.constant.SupportAnswerUrl
import taxi_together.taxi_together.supportAnswer.controller.response.SupportAnswerResponse
import taxi_together.taxi_together.supportAnswer.dto.request.CreateSupportAnswer
import taxi_together.taxi_together.supportAnswer.dto.request.RemoveSupportAnswer
import taxi_together.taxi_together.supportAnswer.service.command.SupportAnswerCommandService
import taxi_together.taxi_together.supportAnswer.service.query.SupportAnswerQueryService

@RestController
class SupportAnswerController @Autowired constructor(
    private val supportAnswerQueryService: SupportAnswerQueryService,
    private val supportAnswerCommandService: SupportAnswerCommandService
) {
    @GetMapping(SupportAnswerUrl.BY_SUPPORT_OPINION)
    fun bySupportOpinion(@PathVariable(SupportAnswerParam.SUPPORT_OPINION_ID) supportOpinionId: Long): ResponseEntity<*> {
        val supportAnswers = supportAnswerQueryService.getSupportAnswerBySupportOpinion(supportOpinionId)
        return SupportAnswerResponse.bySupportOpinionSuccess(supportAnswers)
    }

    @PostMapping(SupportAnswerUrl.CREATE)
    fun create(
        @RequestBody @Valid createSupportAnswer: CreateSupportAnswer,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        logger().info(SupportAnswerControllerLog.CREATE_SUCCESS.log)
        supportAnswerCommandService.createSupportAnswer(createSupportAnswer)

        return SupportAnswerResponse.createSuccess()
    }

    @DeleteMapping(SupportAnswerUrl.REMOVE)
    fun remove(
        @RequestBody @Valid removeSupportAnswer: RemoveSupportAnswer,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        logger().info(SupportAnswerControllerLog.REMOVE_SUCCESS.log)
        supportAnswerCommandService.removeSupportAnswer(removeSupportAnswer)

        return SupportAnswerResponse.removeSuccess()
    }
}