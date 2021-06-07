package chapter6

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch {
            launch {
                delay(200)
                println("child")
            }
            delay(100)
            println("I cancel only my children")
            cancel()
        }
        launch {
            delay(150)
            println("I cancel nobody")
            throw CancellationException()
        }
        launch {
            delay(200)
            println("sibling")
        }
        delay(200)
        println("parent")
    }
}
