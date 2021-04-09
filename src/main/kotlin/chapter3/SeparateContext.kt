package chapter3

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import java.time.Instant

@OptIn(ObsoleteCoroutinesApi::class)
fun main() {
    val context = newSingleThreadContext("single")
    val poolContext = newFixedThreadPoolContext(2, "pool")

    runBlocking {
        repeat(10) {
            launch(poolContext) {
                somethingThatBlocks()
            }
        }
        repeat(5) {
            launch(context) {
                somethingThatSuspends()
            }
        }
    }
}

private fun somethingThatBlocks() {
    Thread.sleep(100)
    println("blocking: ${Instant.now()}")
}

private suspend fun somethingThatSuspends() {
    delay(1)
    println("non-blocking: ${Instant.now()}")
}
