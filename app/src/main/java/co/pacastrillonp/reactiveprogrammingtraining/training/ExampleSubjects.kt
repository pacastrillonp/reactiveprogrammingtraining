package co.pacastrillonp.reactiveprogrammingtraining.training

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject

fun main() {
    rxRelay()
}

private fun behaviorSubjectExample() {
    val behaviorSubject = BehaviorSubject.createDefault(0)
    val subscriptions = CompositeDisposable()
    subscriptions.add(behaviorSubject.subscribeBy {
        println("1) $it")
    })
    println(behaviorSubject.value)
    subscriptions.dispose()
}


private fun publishSubjectExample() {
    val subscriptions = CompositeDisposable()
    val publishSubject = PublishSubject.create<Int>()

    val subscriptionOne = publishSubject.subscribe { int ->
        println(int)
    }

    publishSubject.onNext(1)
    subscriptions.add(subscriptionOne)
}

private fun replaySubjectExample() {
    val subscriptions = CompositeDisposable()
    val replaySubject = ReplaySubject.createWithSize<String>(2)
    replaySubject.onNext("1")
    replaySubject.onNext("2")
    replaySubject.onNext("3")
    subscriptions.add(replaySubject.subscribeBy(
        onNext = { println("1) $it") },
        onError = { println("1) $it") }
    ))
    subscriptions.add(replaySubject.subscribeBy(
        onNext = { println("2) $it") },
        onError = { println("2) $it") }
    ))
    replaySubject.onError(RuntimeException("Error!"))
    replaySubject.onNext("4")
    subscriptions.add(replaySubject.subscribeBy(
        onNext = { println("3) $it") },
        onError = { println("3) $it") }
    ))
}

private fun asyncSubject() {
    val subscriptions = CompositeDisposable()
    val asyncSubject = AsyncSubject.create<Int>()

    subscriptions.add(asyncSubject.subscribeBy(
        onNext = { println("1) $it") },
        onComplete = { println("1) Complete") }
    ))

    asyncSubject.onNext(0)
    asyncSubject.onNext(1)
    asyncSubject.onNext(2)

    asyncSubject.onComplete()
    subscriptions.dispose()
}

private fun rxRelay() {
    val subscriptions = CompositeDisposable()
    val publishRelay = PublishRelay.create<Int>()

    subscriptions.add(publishRelay.subscribeBy(
        onNext = { println("1) $it") }
    ))
    publishRelay.accept(1)
    publishRelay.accept(2)
    publishRelay.accept(3)
}