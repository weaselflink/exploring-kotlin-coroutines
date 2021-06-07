package chapter6

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
            println("I cancel everybody")
            throw Exception()
        }
        launch {
            delay(200)
            println("sibling")
        }
        delay(200)
        println("parent")
    }
}
