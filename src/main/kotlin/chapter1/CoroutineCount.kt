package chapter1

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main() {
    runBlocking {
        launchSwarm().join()
    }
    println("No problem ...")
}

fun CoroutineScope.launchSwarm() =
    launch {
        repeat(100_000) { // launching a million takes some time but succeeds
            launch {
                delay(10_000)
            }
        }
    }
