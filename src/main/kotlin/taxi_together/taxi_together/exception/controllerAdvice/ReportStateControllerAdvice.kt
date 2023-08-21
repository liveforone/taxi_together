package taxi_together.taxi_together.exception.controllerAdvice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import taxi_together.taxi_together.exception.exception.ReportStateException

@RestControllerAdvice
class ReportStateControllerAdvice {
    @ExceptionHandler(ReportStateException::class)
    fun reportStateExceptionHandle(reportStateException: ReportStateException): ResponseEntity<String> {
        return ResponseEntity
            .status(reportStateException.repostStateExceptionMessage.status)
            .body(reportStateException.message)
    }
}