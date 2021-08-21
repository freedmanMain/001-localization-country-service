package country.code.controller.exception.handler

import country.code.exception.AbstractRestApplicationException
import country.code.exception.UnknownIsoCodeApplicationException
import country.code.exception.UnknownLanguageApplicationException
import country.code.exception.NotFoundCountryLocalizationApplicationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(UnknownIsoCodeApplicationException::class, UnknownLanguageApplicationException::class)
    fun handleInvalidException(e: AbstractRestApplicationException) =
        ResponseEntity(provideResponseBody(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(NotFoundCountryLocalizationApplicationException::class)
    fun handleNotFoundException(e: AbstractRestApplicationException) =
        ResponseEntity(provideResponseBody(e, HttpStatus.BAD_REQUEST), HttpStatus.NOT_FOUND)

    fun provideResponseBody(e: AbstractRestApplicationException, status: HttpStatus) =
        mutableMapOf<String, Any>()
            .apply {
                this["timestamp"] = LocalDateTime.now()
                this["status"] = status.value()
                this["error"] = e.errorCode
                this["description"] = e.message.toString()
            }
}
