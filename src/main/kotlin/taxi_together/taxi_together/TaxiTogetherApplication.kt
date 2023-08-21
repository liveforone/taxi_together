package taxi_together.taxi_together

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.HiddenHttpMethodFilter

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

@SpringBootApplication
class TaxiTogetherApplication

fun main(args: Array<String>) {
	runApplication<TaxiTogetherApplication>(*args)

	@Bean
	fun hiddenMethodFilter(): HiddenHttpMethodFilter = HiddenHttpMethodFilter()
}
