package taxi_together.taxi_together.reportState.controller

import taxi_together.taxi_together.reportState.controller.constant.ReportStateParam
import taxi_together.taxi_together.reportState.controller.constant.ReportStateUrl
import taxi_together.taxi_together.reportState.controller.response.ReportStateResponse
import taxi_together.taxi_together.reportState.dto.request.ReportMember
import taxi_together.taxi_together.reportState.service.command.RepostStateCommandService
import taxi_together.taxi_together.reportState.service.query.ReportStateQueryService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import taxi_together.taxi_together.globalUtil.validateBinding
import java.util.UUID

@RestController
class ReportStateController @Autowired constructor(
    private val reportStateQueryService: ReportStateQueryService,
    private val repostStateCommandService: RepostStateCommandService
) {
    @GetMapping(ReportStateUrl.REPORT_STATE_INFO)
    fun reportStateInfo(@PathVariable(ReportStateParam.MEMBER_UUID) memberUUID: UUID): ResponseEntity<*> {
        val reportState = reportStateQueryService.getOneByMemberUUID(memberUUID)
        return ReportStateResponse.infoSuccess(reportState)
    }

    @PostMapping(ReportStateUrl.REPORT)
    fun reportMember(
        @RequestBody @Valid reportMember: ReportMember,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        repostStateCommandService.addRepost(reportMember)
        return ReportStateResponse.reportSuccess()
    }
}