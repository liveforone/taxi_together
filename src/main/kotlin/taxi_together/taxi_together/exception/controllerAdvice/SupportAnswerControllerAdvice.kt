package taxi_together.taxi_together.exception.controllerAdvice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import taxi_together.taxi_together.exception.exception.SupportAnswerException

@RestControllerAdvice
class SupportAnswerControllerAdvice {
    @ExceptionHandler(SupportAnswerException::class)
    fun supportAnswerExceptionHandle(supportAnswerException: SupportAnswerException): ResponseEntity<String> {
        return ResponseEntity
            .status(supportAnswerException.supportAnswerExceptionMessage.status)
            .body(supportAnswerException.message)
    }
}