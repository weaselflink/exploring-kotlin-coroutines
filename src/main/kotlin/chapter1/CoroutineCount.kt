package chapter1

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
                delay(100)
            }
        }
    }
