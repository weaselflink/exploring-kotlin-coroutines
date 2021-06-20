package chapter7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() {
    val channel = Channel<Int>()
    runBlocking {
        startReceivers(channel)
        repeat(10) {
            channel.send(it)
        }
        channel.close()
    }
}

private fun CoroutineScope.startReceivers(channel: Channel<Int>) {
    repeat(4) {
        launch(Dispatchers.Default) {
            var sum = 0
            for (message in channel) {
                sum += message
            }
            println("${Thread.currentThread()}: $sum")
        }
    }
}
