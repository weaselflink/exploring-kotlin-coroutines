package chapter1

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        coroutineScope {
            repeat(1_000_000) {
                launch {
                    delay(1_000)
                }
            }
        }
        println("No problem ...")
    }
}
