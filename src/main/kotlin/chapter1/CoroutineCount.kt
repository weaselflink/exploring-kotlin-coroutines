package chapter1

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val job = launch {
            repeat(1_000_000) {
                launch {
                    delay(Long.MAX_VALUE)
                }
            }
        }
        job.cancel()
    }
    println("No problem ...")
}