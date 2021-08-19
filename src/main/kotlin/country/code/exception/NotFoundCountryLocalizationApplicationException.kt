package country.code.exception

class NotFoundCountryLocalizationApplicationException(
    message: String
) : AbstractRestApplicationException(ERROR_CODE, message) {
    private companion object {
        const val ERROR_CODE = 30
    }
}
