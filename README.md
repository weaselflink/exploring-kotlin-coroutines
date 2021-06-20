# Exploring coroutines

This project explores the possibilities and problems of coroutines via ready to run examples.

## Chapter 1 - Why use coroutines

There is a limit how many threads can be run ([ThreadCount.kt](src/main/kotlin/chapter1/ThreadCount.kt)),
that is surprisingly low (depends on JVM memory).

Coroutines are more resource friendly ([CoroutineCount.kt](src/main/kotlin/chapter1/CoroutineCount.kt))
and can be launched in much higher numbers (think many thousands).

## Chapter 2 - Who executes coroutines

Coroutines still use threads, which can come from different
sources ([WhoExecutesMe.kt](src/main/kotlin/chapter2/WhoExecutesMe.kt)).

Blocking the thread used for coroutine execution defeats
the purpose of using coroutines ([FakeCoroutine.kt](src/main/kotlin/chapter2/FakeCoroutine.kt)).

## Chapter 3 - Handling blocking code

You cannot use code that blocks threads without special precautions, since any snippet
that blocks the thread for a long time blocks a thread of the coroutine context.
When there are no more threads left running, all coroutines will be blocked.

__Solution 1:__ Explicitly launch a thread for each piece of blocking code
([LaunchThread.kt](src/main/kotlin/chapter3/LaunchThread.kt)). This can obviously only be done until we run 
into resource issues.

__Solution 2:__ Run blocking code in a separate context. For example a fixed thread pool
([SeparateContext.kt](src/main/kotlin/chapter3/SeparateContext.kt)). This controls the amount of resources
that can be used by blocking code but makes it difficult to synchronize with the non-blocking code.

__Solution 3:__ Decouple the blocking code in something like a worker or actor 
([Decouple.kt](src/main/kotlin/chapter3/Decouple.kt)). This is essentially the same as solution 2 with
an additional channel for communication.

## Chapter 4 - The `launch()` method

The `launch()` method needs a context, but is not suspending
([LaunchDoesNotSuspend.kt](src/main/kotlin/chapter4/LaunchDoesNotSuspend.kt)).

In a single thread context coroutines created by `launch()` will only be started once the 
calling coroutine suspends, e.g. via `yield()`
([ManuallySuspend.kt](src/main/kotlin/chapter4/ManuallySuspend.kt)).

The `launch()` method returns a `Job` instance, which can be used to control or query the coroutine's state
([LaunchReturnsAJob.kt](src/main/kotlin/chapter4/LaunchReturnsAJob.kt)).

A context with multiple threads might execute the launched coroutine at any time
([LaunchMayBeStartedByAnotherThread.kt](src/main/kotlin/chapter4/LaunchMayBeStartedByAnotherThread.kt)).

Using `async()` is just like `launch()` but it can return a value inside an instance of `Deferred`, which
derives from `Job` so also allows control over the coroutine
([AsyncIsLikeLaunchButReturnsAValue.kt](src/main/kotlin/chapter4/AsyncIsLikeLaunchButReturnsAValue.kt)).

## Chapter 5 - The `CoroutineScope`

Running inside `runBlocking()` blocks the current thread and uses it to execute all coroutines inside it
(see [UsingRunBlocking.kt](src/main/kotlin/chapter5/UsingRunBlocking.kt)).

Calling `coroutineScope()` suspends the current coroutine until the execution inside it terminates, it acts
like `runBlocking()` but inside an existing coroutine. It can also return a value
(see [UsingCoroutineScope.kt](src/main/kotlin/chapter5/UsingCoroutineScope.kt)).

A scope can be created without blocking and outside an existing coroutine
(see [ManualScope.kt](src/main/kotlin/chapter5/ManualScope.kt)).

## Chapter 6 - Cancellation

Throwing an exception in a coroutine cancels all still running coroutines in the same scope. This includes
parents, siblings and children (see [ExceptionCancellation.kt](src/main/kotlin/chapter6/ExceptionCancellation.kt)).

Cancelling a coroutine manually (or throwing a `CancellationException`) will only cancel children 
that are not completed (see [ManualCancellation.kt](src/main/kotlin/chapter6/ManualCancellation.kt)).

Running a coroutine as a child of a `SupervisorJob` will stop any cancellation to propagate out of that coroutine
(see [SupervisorJobCancellation.kt](src/main/kotlin/chapter6/SupervisorJobCancellation.kt)).

## Chapter 7 - Channels

## Chapter 8 - Use cases

There is no need for starting a thread for each call to a remote endpoint 
([ServiceCaller.kt](src/main/kotlin/chapter8/ServiceCaller.kt)).

While not a true actor model ([see Wikipedia](https://en.wikipedia.org/wiki/Actor_model)), 
if you think of `CompletableDeferred` of a channel that only accepts a single
message, it comes pretty close. The main function of this actor is to encapsulate mutable state
([ActorWithInternalState.kt](src/main/kotlin/chapter8/ActorWithInternalState.kt)).

Actors do not have to send back messages to the caller, they can create new actors which do this
for example to fetch data concurrently
([ActorWithFanOut.kt](src/main/kotlin/chapter8/ActorWithFanOut.kt)).
