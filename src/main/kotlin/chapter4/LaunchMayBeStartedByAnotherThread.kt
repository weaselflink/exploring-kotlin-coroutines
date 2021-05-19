package chapter4

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

fun main() {
    val poolContext = Executors.newFixedThreadPool(10).asCoroutineDispatcher()

    runBlocking(poolContext) {
        repeat(5) {
            launch {
                println("I may execute immediately ($it)")
            }
        }
        println("I get called somewhere in between")
    }

    poolContext.close()
}
