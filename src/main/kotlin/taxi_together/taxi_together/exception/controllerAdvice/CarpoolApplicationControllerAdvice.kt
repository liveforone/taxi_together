package taxi_together.taxi_together.exception.controllerAdvice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import taxi_together.taxi_together.exception.exception.CarpoolApplicationException

@RestControllerAdvice
class CarpoolApplicationControllerAdvice {
    @ExceptionHandler(CarpoolApplicationException::class)
    fun carpoolApplicationExceptionHandle(carpoolException: CarpoolApplicationException): ResponseEntity<String> {
        return ResponseEntity
            .status(carpoolException.carpoolApplicationExceptionMessage.status)
            .body(carpoolException.message)
    }
}