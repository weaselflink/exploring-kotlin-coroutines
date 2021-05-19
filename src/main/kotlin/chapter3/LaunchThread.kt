package chapter3

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.concurrent.thread

fun main() {
    runUsingSomeContexts {
        runBlocking(context) {
            val threads = mutableListOf<Thread>()

            repeat(10) {
                threads += thread {
                    somethingThatBlocks()
                }
            }
            repeat(5) {
                launch {
                    somethingThatSuspends()
                }
            }

            // wait for thread termination in a non blocking way
            // without using join()
            launch {
                while (threads.any { it.isAlive }) {
                    // delay(0) does not suspend and would block the thread
                    yield()
                }
            }
        }
    }
}
