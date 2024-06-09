package ru.dekabrsky.easylife.di.provider.network

import io.reactivex.Scheduler
import retrofit2.CallAdapter
import javax.inject.Inject
import javax.inject.Provider

class CallAdapterFactoryProvider @Inject constructor(
    private val scheduler: Scheduler,
    private var baseModelErrorHandlerFactory: BaseModelErrorHandlerFactory
) : Provider<CallAdapter.Factory> {
    override fun get(): CallAdapter.Factory = RxErrorHandlingFactory(scheduler, baseModelErrorHandlerFactory)
}