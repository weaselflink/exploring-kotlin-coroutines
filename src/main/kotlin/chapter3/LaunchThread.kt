package chapter3

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.yield
import java.time.Instant
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

@OptIn(ObsoleteCoroutinesApi::class)
fun main() {
    val context = newSingleThreadContext("single")

    runBlocking(context) {
        val threads = mutableListOf<Thread>()

        repeat(10) {
            threads += thread {
                somethingThatBlocks()
            }
        }
        repeat(5) {
            launch {
                somethingThatSuspends()
            }
        }

        // wait for thread termination in a non blocking way
        // without using join()
        launch {
            while (threads.any { it.isAlive }) {
                // delay(0) does not suspend and would block the thread
                yield()
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
