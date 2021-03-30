package chapter2

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import java.time.Instant

@OptIn(ObsoleteCoroutinesApi::class)
fun main() {
    val context = newSingleThreadContext("single thread")

    runBlocking {
        repeat(10) {
            launch(context) {
                // this "coroutine" blocks the (single) thread
                // and forces sequential execution
                Thread.sleep(100)
                println("blocking: ${Instant.now()}")
            }
        }
        repeat(10) {
            launch(context) {
                // non-blocking (suspend) function
                // allows parallel execution
                delay(100)
                println("non-blocking: ${Instant.now()}")
            }
        }
    }
}

