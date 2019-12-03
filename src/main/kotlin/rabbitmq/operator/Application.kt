package rabbitmq.operator

import io.micronaut.runtime.Micronaut

internal object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("rabbitmq.operator")
                .mainClass(Application.javaClass)
                .start()
    }
}