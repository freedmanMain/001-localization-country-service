package country.code

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CountryCodeServiceApplication

fun main(args: Array<String>) {
    runApplication<CountryCodeServiceApplication>(*args)
}
