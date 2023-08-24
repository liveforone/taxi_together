package taxi_together.taxi_together.bankbook.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import taxi_together.taxi_together.bankbook.controller.constant.BankbookControllerLog
import taxi_together.taxi_together.bankbook.controller.constant.BankbookParam
import taxi_together.taxi_together.bankbook.controller.constant.BankbookUrl
import taxi_together.taxi_together.bankbook.controller.response.BankbookResponse
import taxi_together.taxi_together.bankbook.dto.update.UpdateBankbook
import taxi_together.taxi_together.bankbook.service.command.BankbookCommandService
import taxi_together.taxi_together.bankbook.service.query.BankbookQueryService
import taxi_together.taxi_together.globalUtil.validateBinding
import taxi_together.taxi_together.logger
import java.util.*

@RestController
class BankbookController @Autowired constructor(
    private val bankbookQueryService: BankbookQueryService,
    private val bankbookCommandService: BankbookCommandService
) {
    @GetMapping(BankbookUrl.BANKBOOK_INFO)
    fun bankbookInfo(@PathVariable(BankbookParam.MEMBER_UUID) memberUUID: UUID): ResponseEntity<*> {
        val bankbook = bankbookQueryService.getBankbookByMember(memberUUID)
        return BankbookResponse.infoSuccess(bankbook)
    }

    @PutMapping(BankbookUrl.UPDATE_INFO)
    fun updateInfo(
        @RequestBody @Valid updateBankbook: UpdateBankbook,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        bankbookCommandService.updateBankbook(updateBankbook)
        logger().info(BankbookControllerLog.UPDATE_INFO_SUCCESS.log)

        return BankbookResponse.updateInfoSuccess()
    }
}