package ru.dekabrsky.easylife.basic.presenter

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.rx.RxDisposer
import ru.dekabrsky.easylife.basic.rx.RxSchedulers

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

    protected fun <T> Single<T>.subscribeOnIo() =
        this.subscribeOn(RxSchedulers.io())
            .observeOn(RxSchedulers.main())

    protected fun <T> Observable<T>.subscribeOnIo(): Observable<T> =
        this.subscribeOn(RxSchedulers.io())
            .observeOn(RxSchedulers.main())

    protected fun Completable.subscribeOnIo() =
        this.subscribeOn(RxSchedulers.io())
            .observeOn(RxSchedulers.main())
    
    open fun onBackPressed() { router?.back() }
}