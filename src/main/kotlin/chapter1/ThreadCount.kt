package chapter1

import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main() {
    var count = 0
    try {
        while (true) {
            thread {
                TimeUnit.MINUTES.sleep(1)
            }
            count++
        }
    } catch (ex: Throwable) {
        println("Could start $count threads before encountering ${ex::class.simpleName}")
    } finally {
        exitProcess(0)
    }
}
