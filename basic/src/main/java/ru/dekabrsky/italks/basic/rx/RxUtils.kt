package ru.dekabrsky.italks.basic.rx

import io.reactivex.Completable
import io.reactivex.Single
import ru.dekabrsky.italks.basic.fragments.BasicView

fun <T> Single<T>.withLoadingView(view: BasicView) =
    this.doOnSubscribe { view.setLoadingVisibility(true) }
        .doFinally { view.setLoadingVisibility(false) }

fun <T> Single<T>.withLoadingViewIf(view: BasicView, condition: Boolean) =
    if (condition) {
        this.doOnSubscribe { view.setLoadingVisibility(true) }
            .doFinally { view.setLoadingVisibility(false) }
    } else this

fun Completable.withLoadingView(view: BasicView) =
    this.doOnSubscribe { view.setLoadingVisibility(true) }
        .doFinally { view.setLoadingVisibility(false) }