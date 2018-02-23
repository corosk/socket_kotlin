package book.application

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class BookApplication{
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(BookApplication::class.java, *args)
        }
    }
}