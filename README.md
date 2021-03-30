# Exploring coroutines

This project tries to explore the possibilities and problems of coroutines via ready to run examples.

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
