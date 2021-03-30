# Chapter 2

Coroutines still use threads, which can come from different 
sources ([WhoExecutesMe.kt](WhoExecutesMe.kt)).

Blocking the thread used for coroutine execution defeats 
the purpose of using coroutines ([FakeCoroutine.kt](FakeCoroutine.kt)).
