package chapter4

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() {
    runBlocking {
        launch {
            println("I am first")
        }
        yield()
        println("I am second")
    }
}
