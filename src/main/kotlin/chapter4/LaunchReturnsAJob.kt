package chapter4

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val job: Job = launch {
            println("I am never executed")
        }

        job.cancel()
    }
}
