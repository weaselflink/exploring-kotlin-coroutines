package chapter4

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch {
            println("I am second")
        }
        println("I am first")
    }
}
