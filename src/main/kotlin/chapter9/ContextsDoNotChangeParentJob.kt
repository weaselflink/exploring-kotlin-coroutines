package chapter9

import kotlinx.coroutines.*

fun main() {
    runBlockingCatching {
        launch {
            launch(Dispatchers.Default) { // different dispatcher, same parent job
                delay(100)
                throw Exception()
            }
            println("child jobs: ${coroutineContext.job.children.toList()}")
            delay(200)
            println("cancelled")
        }
    }
    runBlockingCatching {
        val scope = CoroutineScope(Dispatchers.Default)
        launch {
            scope.launch { // different dispatcher and parent job
                delay(100)
                throw Exception()
            }
            println("child jobs: ${coroutineContext.job.children.toList()}")
            delay(200)
            println("not cancelled")
        }
    }
}

private fun runBlockingCatching(block: suspend CoroutineScope.() -> Unit) {
    try {
        runBlocking {
            block()
        }
    } catch (ex: Exception) {
    }
}
