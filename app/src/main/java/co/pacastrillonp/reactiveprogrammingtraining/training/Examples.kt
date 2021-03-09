package co.pacastrillonp.reactiveprogrammingtraining.training

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import java.io.File
import java.io.FileNotFoundException

fun main() {
    single()
}

private fun single() {
    val subscriptions = CompositeDisposable()

    val observer = loadText("Copyright")
        .subscribeBy(
            onSuccess = { println(it) },
            onError = { println("Error, $it") }
        )

    subscriptions.add(observer)
}

private fun loadText(filename: String): Single<String> {

    return Single.create create@{ emitter ->

        val file = File(filename)

        if (!file.exists()) {
            emitter.onError(FileNotFoundException("Can’t find $filename"))
            return@create
        }
        val contents = file.readText(Charsets.UTF_8)

        emitter.onSuccess(contents)
    }
}
