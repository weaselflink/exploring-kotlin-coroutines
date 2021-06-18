package chapter7

import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun main() {
    printTime {
        runBlocking {
            supervisorScope {
                repeat(10) { counter ->
                    launch {
                        if (Random.nextBoolean()) throw Exception()
                        getAsyncWithHttpClient("https://httpbin.org/delay/2").also {
                            println("finished $counter status ${it.statusCode()}")
                            println(it.statusCode())
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
private fun printTime(block: () -> Unit) {
    measureTime {
        block()
    }.run {
        println("time: $inWholeMilliseconds ms")
    }
}

private suspend fun getAsyncWithHttpClient(url: String): HttpResponse<String> =
    HttpClient
        .newHttpClient()
        .sendAsync(
            HttpRequest.newBuilder(URI(url)).GET().build(),
            HttpResponse.BodyHandlers.ofString()
        )
        .await()
