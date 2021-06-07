package chapter6

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val job = SupervisorJob()
        launch(job) {
            launch(job) {
                delay(200)
                println("child")
            }
            delay(100)
            println("I cancel nobody")
            throw Exception()
        }
        launch(job) {
            delay(200)
            println("sibling")
        }
        delay(200)
        println("parent")
    }
}
