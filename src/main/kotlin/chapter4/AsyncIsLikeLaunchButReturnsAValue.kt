package chapter4

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val result: Deferred<String> = async {
            "I am second"
        }
        println("I am first")
        println(result.await())
        val job: Job = result
        println("Coroutine is completed: ${job.isCompleted}")
    }
}
