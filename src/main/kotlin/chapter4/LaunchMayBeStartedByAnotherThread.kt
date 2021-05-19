package chapter4

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

fun main() {
    Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        .use { poolContext ->
            runBlocking(poolContext) {
                repeat(5) {
                    launch {
                        println("I may execute immediately ($it)")
                    }
                }
                println("I get called somewhere in between")
            }
        }
}
