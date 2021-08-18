package country.code.exception

class CountryNotFoundApplicationException(
    message: String
) : AbstractRestApplicationException(ERROR_CODE, message) {
    private companion object {
        const val ERROR_CODE = 40
    }
}
