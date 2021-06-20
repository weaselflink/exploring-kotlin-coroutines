package chapter8

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal
import kotlin.random.Random

fun main() {
    runBlocking {
        val actor = shoppingCartActor()
        coroutineScope {
            repeat(10) {
                launch {
                    val request = ShoppingCartPriceRequest("dummy")
                    actor.send(request)
                    println(request.result.await())
                }
            }
            println("waiting for results ...")
        }
        actor.close()
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.shoppingCartActor() =
    actor<ShoppingCartPriceRequest> {
        for (message in channel) {
            val aggregator = itemAggregatorActor(message.result)
            getShoppingCart(message.id)
                .forEach { aggregator.send(ItemPriceRequest(it)) }
            aggregator.close()
        }
    }

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.itemAggregatorActor(result: CompletableDeferred<BigDecimal>) =
    actor<ItemPriceRequest>(capacity = Channel.UNLIMITED) {
        var sum = BigDecimal.ZERO
        for (message in channel) {
            sum += getItemPrice(message.id)
        }
        result.complete(sum)
    }

private fun getShoppingCart(id: String) =
    listOf("item1", "item2")

private suspend fun getItemPrice(id: String): BigDecimal {
    delay(Random.nextLong(500))
    return BigDecimal(Random.nextInt(99) + 1)
}

data class ShoppingCartPriceRequest(
    val id: String,
    val result: CompletableDeferred<BigDecimal> = CompletableDeferred()
)

data class ItemPriceRequest(
    val id: String
)
