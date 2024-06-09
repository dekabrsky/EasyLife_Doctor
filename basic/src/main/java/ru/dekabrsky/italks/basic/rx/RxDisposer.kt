package ru.dekabrsky.easylife.basic.rx

import io.reactivex.disposables.Disposable

interface RxDisposer {
    fun add(disposable: Disposable)

    companion object {

        fun by(delegate: (Disposable) -> Unit): RxDisposer {
            return object : RxDisposer {
                override fun add(disposable: Disposable) {
                    delegate.invoke(disposable)
                }
            }
        }
    }
}