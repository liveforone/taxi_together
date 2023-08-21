package taxi_together.taxi_together.exception.controllerAdvice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import taxi_together.taxi_together.exception.exception.BankbookException

@RestControllerAdvice
class BankbookControllerAdvice {
    @ExceptionHandler(BankbookException::class)
    fun bankbookExceptionHandle(bankbookException: BankbookException): ResponseEntity<*> {
        return ResponseEntity
            .status(bankbookException.bankbookExceptionMessage.status)
            .body(bankbookException.message)
    }
}