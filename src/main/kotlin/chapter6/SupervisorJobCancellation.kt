package chapter6

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() {
    runBlocking {
        supervisorScope {
            launch {
                launch {
                    delay(200)
                    println("child")
                }
                delay(100)
                println("I cancel nobody")
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
}
