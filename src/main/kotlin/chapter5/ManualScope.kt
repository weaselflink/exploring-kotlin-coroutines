package chapter5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

fun main() {
    val context = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    val scope = CoroutineScope(context)
    val job = scope.launch {
        println("I am a coroutine")
    }
    println("I am regular code")
    while (!job.isCompleted) { Thread.sleep(10) }
    context.close()
}
