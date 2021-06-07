package chapter5

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    println("main method thread: ${Thread.currentThread().name}")
    runBlocking {
        launch {
            println("launch thread: ${Thread.currentThread().name}")
        }
        println("runBlocking thread: ${Thread.currentThread().name}")
    }
}
