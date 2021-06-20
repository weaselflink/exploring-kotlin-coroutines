package chapter7

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val channel = Channel<String>()
        launch {
            delay(2000)
            println(channel.receive())
        }
        channel.send("this will be received after 2 seconds")
        println("this will also have to wait")
    }
}
