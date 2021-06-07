package chapter5

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        coroutineScope {
            println("I am first")
        }
        println("I am second")
    }
}
