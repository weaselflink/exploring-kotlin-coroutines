package chapter1

import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main() {
    var count = 0
    try {
        while (true) {
            thread {
                Thread.sleep(Long.MAX_VALUE)
            }
            count++
        }
    } catch (ex: Throwable) {
        println("Could start $count threads before encountering ${ex::class.simpleName}")
    } finally {
        exitProcess(0)
    }
}