package chapter3

import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import java.time.Instant
import java.util.concurrent.Executors

class Contexts : AutoCloseable {

    val context: ExecutorCoroutineDispatcher =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    val poolContext: ExecutorCoroutineDispatcher =
        Executors.newFixedThreadPool(2).asCoroutineDispatcher()

    override fun close() {
        // ensures method using the contexts terminates
        context.close()
        poolContext.close()
    }
}

fun runUsingSomeContexts(block: Contexts.() -> Unit) {
    Contexts().use(block)
}

fun somethingThatBlocks() {
    Thread.sleep(100)
    println("blocking: ${Instant.now()}")
}

suspend fun somethingThatSuspends() {
    delay(1)
    println("non-blocking: ${Instant.now()}")
}
