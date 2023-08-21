package taxi_together.taxi_together.globalUtil


import taxi_together.taxi_together.exception.exception.BindingException
import org.springframework.validation.BindingResult
import java.util.*

fun validateBinding(bindingResult: BindingResult) {
    if (bindingResult.hasErrors()) {
        val errorMessage = Objects
            .requireNonNull(bindingResult.fieldError)
            ?.defaultMessage
        throw errorMessage?.let { BindingException(it) }!!
    }
}