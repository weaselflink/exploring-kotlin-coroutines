package chapter8

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val actor = createActor()
        coroutineScope {
            repeat(10) {
                launch {
                    actor.send(CounterAction.Add(it + 1))
                }
            }
        }
        CounterAction.Get()
            .also {
                actor.send(it)
                println(it.answer.await())
            }
        actor.close()
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.createActor() =
    actor<CounterAction> {
        var counter = 0
        for (message in channel) {
            when (message) {
                is CounterAction.Add -> {
                    counter += message.value
                }
                is CounterAction.Get -> {
                    message.answer.complete(counter)
                }
            }
        }
    }

sealed class CounterAction {

    class Get(
        val answer: CompletableDeferred<Int> = CompletableDeferred()
    ) : CounterAction()

    class Add(val value: Int) : CounterAction()
}
