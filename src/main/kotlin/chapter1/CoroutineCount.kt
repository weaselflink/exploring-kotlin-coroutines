package chapter1

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val job = launchSwarm()
        job.cancel()
    }
    println("No problem ...")
}

fun CoroutineScope.launchSwarm() =
    launch {
        repeat(1_000_000) {
            launch {
                delay(Long.MAX_VALUE)
            }
        }
    }
