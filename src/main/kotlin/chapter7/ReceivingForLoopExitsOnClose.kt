package chapter7

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val channel = Channel<String>()
        launch {
            for (message in channel) {
                println("received: $message")
            }
            println("stopped receiving")
        }
        channel.send("message 1")
        channel.send("message 2")
        channel.close()
    }
}
