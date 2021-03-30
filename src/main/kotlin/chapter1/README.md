# Chapter 1

There is a limit how many threads can be run ([ThreadCount.kt](ThreadCount.kt)), 
that is surprisingly low (depends on JVM memory).

Coroutines are more resource friendly ([CoroutineCount.kt](CoroutineCount.kt))
and can be launched in much higher numbers (think many thousands).
