package zebra.zyber.epubtranslator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EpubTranslatorApplication

fun main(args: Array<String>) {
    runApplication<EpubTranslatorApplication>(*args)
}
