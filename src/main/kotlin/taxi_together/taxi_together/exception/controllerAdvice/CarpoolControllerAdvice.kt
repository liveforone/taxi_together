package taxi_together.taxi_together.exception.controllerAdvice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import taxi_together.taxi_together.exception.exception.CarpoolException

@RestControllerAdvice
class CarpoolControllerAdvice {
    @ExceptionHandler(CarpoolException::class)
    fun carpoolExceptionHandle(carpoolException: CarpoolException): ResponseEntity<String> {
        return ResponseEntity
            .status(carpoolException.carpoolExceptionMessage.status)
            .body(carpoolException.message)
    }
}