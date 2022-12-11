package ru.dekabrsky.italks.basic.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.rx.RxDisposer

abstract class BasicPresenter<T : MvpView> constructor(private val router: FlowRouter? = null) :
    MvpPresenter<T>() {

    protected val fullLifecycleDisposer = RxDisposer.by { it.addFullLifeCycle() }

    private val fullLifeCycleCompositeDisposable = CompositeDisposable()
    private val viewLifeCycleCompositeDisposable = CompositeDisposable()


    override fun attachView(view: T) = super.attachView(view)

    override fun destroyView(view: T) {
        super.destroyView(view)
        viewLifeCycleCompositeDisposable.clear()
    }

    override fun onDestroy() {
        viewLifeCycleCompositeDisposable.dispose()
        fullLifeCycleCompositeDisposable.dispose()
    }

    protected fun Disposable.addFullLifeCycle(): Disposable {
        fullLifeCycleCompositeDisposable.add(this)
        return this
    }

    protected fun Disposable.addViewLifeCycle(): Disposable {
        viewLifeCycleCompositeDisposable.add(this)
        return this
    }

    open fun onBackPressed() { router?.back() }
}