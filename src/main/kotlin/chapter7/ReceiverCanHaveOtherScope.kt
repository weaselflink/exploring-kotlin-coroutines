package chapter7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() {
    val channel = Channel<Int>()
    startReceivers(channel)
    runBlocking {
        repeat(10) {
            channel.send(it)
        }
        channel.close()
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
private fun startReceivers(channel: Channel<Int>) {
    val scope = CoroutineScope(newSingleThreadContext("dummy"))
    repeat(4) {
        scope.launch {
            var sum = 0
            for (message in channel) {
                sum += message
            }
            println(sum)
        }
    }
}
