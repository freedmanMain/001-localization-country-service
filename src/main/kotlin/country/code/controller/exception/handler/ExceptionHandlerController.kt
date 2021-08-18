package country.code.controller.exception.handler

import country.code.exception.AbstractRestApplicationException
import country.code.exception.UnknownIsoCodeApplicationException
import country.code.exception.UnknownLanguageApplicationException
import country.code.exception.CountryNotFoundApplicationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(UnknownIsoCodeApplicationException::class, UnknownLanguageApplicationException::class)
    fun handleInvalidException(e: AbstractRestApplicationException) =
        ResponseEntity(provideResponseBody(e), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(CountryNotFoundApplicationException::class)
    fun handleNotFoundException(e: AbstractRestApplicationException) =
        ResponseEntity(provideResponseBody(e), HttpStatus.NOT_FOUND)


    fun provideResponseBody(e: AbstractRestApplicationException) = mutableMapOf<String, Any>()
        .apply {
            this["error"] = e.errorCode
            this["message"] = e.message.toString()
        }
}
