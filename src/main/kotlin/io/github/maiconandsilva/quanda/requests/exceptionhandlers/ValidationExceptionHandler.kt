package io.github.maiconandsilva.quanda.requests.exceptionhandlers

import io.github.maiconandsilva.quanda.schemas.ErrorResponse
import io.github.maiconandsilva.quanda.schemas.ErrorType
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ResponseBody

import java.util.*



@ControllerAdvice
class ValidationExceptionHandler(
    private val messageSource: MessageSource
) {
    // DisabledException
//    HttpMessageNotReadableException
// BadCredentialsException
//    CredentialsExpiredException
//    DisabledException
//    org.springframework.dao.DataIntegrityViolationException
//    org.hibernate.exception.ConstraintViolationException

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception::class)
//    fun handleAllExceptions(ex: Exception, request: WebRequest): ErrorResponse {
//        val details: Map<String, List<String>> = mapOf("general" to listOf(ex.localizedMessage))
//        return ErrorResponse(ErrorType.SERVER_ERROR, details)
//    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleNotValidException(ex: MethodArgumentNotValidException): ErrorResponse {
        val errors = mutableMapOf<String, MutableList<String>>().withDefault { mutableListOf() }

        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = messageSource.getMessage(error, Locale.ROOT) ?: error.defaultMessage
            if (errorMessage != null) {
                errors[fieldName]?.add(errorMessage)
            }
        }
        return ErrorResponse(ErrorType.VALIDATION, errors)
    }
}
