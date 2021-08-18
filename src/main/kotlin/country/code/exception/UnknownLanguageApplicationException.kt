package country.code.exception

class UnknownLanguageApplicationException(
    message: String
) : AbstractRestApplicationException(ERROR_CODE, message) {
    private companion object {
        const val ERROR_CODE = 20
    }
}
