package chapter3

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runUsingSomeContexts {
        runBlocking(context) {
            repeat(10) {
                launch(poolContext) {
                    somethingThatBlocks()
                }
            }
            repeat(5) {
                launch {
                    somethingThatSuspends()
                }
            }
        }
    }
}
