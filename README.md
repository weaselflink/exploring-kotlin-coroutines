# Exploring coroutines

This project explores the possibilities and problems of coroutines via ready to run examples.

## Chapter 1

There is a limit how many threads can be run ([ThreadCount.kt](src/main/kotlin/chapter1/ThreadCount.kt)),
that is surprisingly low (depends on JVM memory).

Coroutines are more resource friendly ([CoroutineCount.kt](src/main/kotlin/chapter1/CoroutineCount.kt))
and can be launched in much higher numbers (think many thousands).

## Chapter 2

Coroutines still use threads, which can come from different
sources ([WhoExecutesMe.kt](src/main/kotlin/chapter2/WhoExecutesMe.kt)).

Blocking the thread used for coroutine execution defeats
the purpose of using coroutines ([FakeCoroutine.kt](src/main/kotlin/chapter2/FakeCoroutine.kt)).

## Chapter 3

You cannot use code that blocks threads without special precautions, since any snippet
that blocks the thread for a long time blocks all coroutines on the same context.

__Solution 1:__ Explicitly launch a thread for each piece of blocking code
([LaunchThread.kt](src/main/kotlin/chapter3/LaunchThread.kt)). This can obviously only be done until we run 
into resource issues.

__Solution 2:__ Run blocking code in a separate context. For example a fixed thread pool
([SeparateContext.kt](src/main/kotlin/chapter3/SeparateContext.kt)). This controls the amount of resources
that can be used by blocking code but makes it difficult to synchronize with the non-blocking code.

__Solution 3:__ Decouple the blocking code in something like a worker or actor 
([Decouple.kt](src/main/kotlin/chapter3/Decouple.kt)). This is essentially the same as solution 2 with
an additional channel for communication.
