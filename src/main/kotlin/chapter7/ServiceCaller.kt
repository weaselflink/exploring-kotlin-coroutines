package chapter7

import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    measureTime {
        runBlocking {
            repeat(10) {
                launch {
                    val response = getAsyncWithHttpClient("https://httpbin.org/delay/2")
                    response.await().body()
                    println("finished $it")
                }
            }
        }
    }.also {
        println("time: ${it.inWholeMilliseconds}ms")
    }
}

fun getAsyncWithHttpClient(url: String): CompletableFuture<HttpResponse<String>> =
    HttpClient
        .newHttpClient()
        .sendAsync(
            HttpRequest.newBuilder(URI(url)).GET().build(),
            HttpResponse.BodyHandlers.ofString()
        )
