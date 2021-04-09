package chapter3

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import java.time.Instant

@OptIn(ObsoleteCoroutinesApi::class)
fun main() {
    val context = newSingleThreadContext("single")

    runBlocking {
        val channel = startWorker(context)

        repeat(10) {
            launch(context) {
                CompletableDeferred<Instant>().also { msg ->
                    channel.send(msg)
                    println("blocking: ${msg.await()}")
                }
            }
        }
        repeat(5) {
            launch(context) {
                somethingThatSuspends()
            }
        }
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
private fun CoroutineScope.startWorker(context: CoroutineDispatcher): SendChannel<CompletableDeferred<Instant>> =
    Channel<CompletableDeferred<Instant>>().also { channel ->

        val poolContext = newFixedThreadPoolContext(2, "pool")
        var count = 0

        // the part of the worker that handles messages
        // still uses the original single thread context
        launch(context) {
            for (msg in channel) {
                launch(poolContext) {
                    msg.complete(somethingThatBlocks())
                }
                if (count++ >= 10) break
            }
        }
    }

private fun somethingThatBlocks(): Instant {
    Thread.sleep(100)
    return Instant.now()
}

private suspend fun somethingThatSuspends() {
    delay(1)
    println("non-blocking: ${Instant.now()}")
}
