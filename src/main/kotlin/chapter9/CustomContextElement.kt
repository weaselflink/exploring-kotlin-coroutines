package chapter9

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

fun main() {
    runBlocking {
        launch(ContextValue("test1")) {
            printValue()
            coroutineContext[ContextValue.Key]
                ?.let { it.value = "test2" }
            printValue()
        }
    }
}

private suspend fun printValue() {
    println(coroutineContext[ContextValue.Key]?.value)
}

private data class ContextValue(
    var value: String
) : AbstractCoroutineContextElement(ContextValue) {

    companion object Key : CoroutineContext.Key<ContextValue>
}
