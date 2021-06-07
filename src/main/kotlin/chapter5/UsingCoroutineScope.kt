package chapter5

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val a = coroutineScope {
            println("I am first")
            5
        }
        println("I am second")
    }
}
