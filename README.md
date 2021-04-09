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

Solution 1: Run blocking code in a separate context. For example a fixed thread pool
([RunBlockingCodeSeparately.kt](src/main/kotlin/chapter3/RunBlockingCodeSeparately.kt)).
