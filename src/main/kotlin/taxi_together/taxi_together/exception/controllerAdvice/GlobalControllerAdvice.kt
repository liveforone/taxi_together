package taxi_together.taxi_together.exception.controllerAdvice

import taxi_together.taxi_together.exception.controllerAdvice.constant.GlobalAdviceConstant
import taxi_together.taxi_together.exception.exception.BindingException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun duplicateEntityValueExceptionHandle(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(GlobalAdviceConstant.DUPLICATE_ENTITY_VAL)
    }

    @ExceptionHandler(BindingException::class)
    fun bindingExceptionHandle(bindingException: BindingException): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(bindingException.message)
    }
}