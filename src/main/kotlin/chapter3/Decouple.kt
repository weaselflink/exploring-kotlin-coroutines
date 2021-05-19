package chapter3

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant

fun main() {
    runUsingSomeContexts {
        runBlocking(context) {
            val channel = startWorker(poolContext)

            repeat(10) {
                launch {
                    sendToQueue(channel)
                }
            }
            repeat(5) {
                launch {
                    somethingThatSuspends()
                }
            }
        }
    }
}

private suspend fun sendToQueue(channel: Channel<CompletableDeferred<Instant>>) {
    CompletableDeferred<Instant>().also { msg ->
        channel.send(msg)
        println("blocking: ${msg.await()}")
    }
}

private fun CoroutineScope.startWorker(poolContext: ExecutorCoroutineDispatcher) =
    Channel<CompletableDeferred<Instant>>().also { channel ->

        var count = 0

        // the part of the worker that handles messages
        // still uses the original single thread context
        launch {
            for (msg in channel) {
                launch(poolContext) {
                    msg.complete(somethingThatBlocksAndReturns())
                }
                // stop after 10 messages to allow main method to terminate
                if (++count >= 10) break
            }
        }
    }

private fun somethingThatBlocksAndReturns(): Instant {
    Thread.sleep(100)
    return Instant.now()
}
