package chapter2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

@OptIn(ObsoleteCoroutinesApi::class)
fun main() {
    runBlocking {
        runSwarm(newFixedThreadPoolContext(2, "two thread"))
        runSwarm(newSingleThreadContext("single thread"))
        runSwarm(Dispatchers.Default)
        runSwarm(Dispatchers.IO)
        runSwarm(Dispatchers.Unconfined)
    }
}

fun CoroutineScope.runSwarm(context: CoroutineContext) {
    val swarmSize = 100
    val channel = Channel<Long>(Channel.UNLIMITED)
    repeat(swarmSize) {
        launch(context) {
            channel.send(Thread.currentThread().id)
            delay(100)
        }
    }
    launch(context) {
        val count = flow {
            repeat(swarmSize) {
                emit(channel.receive())
            }
        }.toList().distinct().count()

        println("${Thread.currentThread().name.substringBefore(" @").substringBefore("-")}: thread count $count")
    }
}