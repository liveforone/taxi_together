package taxi_together.taxi_together.exception.controllerAdvice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import taxi_together.taxi_together.exception.exception.SupportOpinionException

@RestControllerAdvice
class SupportOpinionControllerAdvice {
    @ExceptionHandler(SupportOpinionException::class)
    fun supportOpinionExceptionHandle(supportOpinionException: SupportOpinionException): ResponseEntity<String> {
        return ResponseEntity
            .status(supportOpinionException.supportOpinionExceptionMessage.status)
            .body(supportOpinionException.message)
    }
}