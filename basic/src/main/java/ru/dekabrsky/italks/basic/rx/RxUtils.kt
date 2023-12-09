package ru.dekabrsky.italks.basic.rx

import io.reactivex.Completable
import io.reactivex.Single
import ru.dekabrsky.italks.basic.fragments.BasicView

fun <T> Single<T>.withLoadingView(view: BasicView) =
    this.doOnSubscribe { view.setLoadingVisibility(true) }
        .doFinally { view.setLoadingVisibility(false) }

fun <T> Single<T>.withLoadingViewIf(view: BasicView, condition: Boolean) =
    if (condition) this.withLoadingView(view) else this

fun <T> Single<T>.withCustomLoadingView(view: (Boolean) -> Unit) =
    this.doOnSubscribe { view.invoke(true) }
        .doFinally { view.invoke(false) }

fun <T> Single<T>.withCustomLoadingViewIf(view: (Boolean) -> Unit, condition: Boolean) =
    if (condition) this.withCustomLoadingView(view)else this

fun Completable.withLoadingView(view: BasicView) =
    this.doOnSubscribe { view.setLoadingVisibility(true) }
        .doFinally { view.setLoadingVisibility(false) }