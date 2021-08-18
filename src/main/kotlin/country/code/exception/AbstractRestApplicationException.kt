package country.code.exception

abstract class AbstractRestApplicationException(
    val errorCode: Int,
    message: String
) : RuntimeException(message)
