package ru.dekabrsky.easylife.di.provider.network

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RxErrorHandlingFactory(
    scheduler: Scheduler,
    private var baseModelErrorHandlerFactory: BaseModelErrorHandlerFactory
) : CallAdapter.Factory() {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private val original = RxJava2CallAdapterFactory.createWithScheduler(scheduler)

    @Suppress("UseExpressionBody", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*, *> {
        return RxCallAdapterWrapper(
            original.get(returnType, annotations, retrofit) as CallAdapter<*, *>,
            baseModelErrorHandlerFactory
        )
    }

    class RxCallAdapterWrapper<T>(
        private val wrapped: CallAdapter<T, *>,
        private val baseModelErrorHandlerFactory: BaseModelErrorHandlerFactory
    ) : CallAdapter<T, Any> {

        override fun responseType(): Type = wrapped.responseType()

        override fun adapt(call: Call<T>): Any {
            val delegate = wrapped.adapt(call)
            return when (delegate) {
                is Observable<*> -> wrapObservable(delegate)
                is Single<*> -> wrapSingle(delegate)
                is Completable -> delegate
                else -> throw IllegalArgumentException("Unsupported type ${delegate.javaClass.name}")
            }
        }

        private fun wrapObservable(observable: Observable<*>): Observable<*> {
            return observable
                .flatMap(this::handleErrorIfOptionsForObservable)
        }

        private fun wrapSingle(single: Single<*>): Single<*> {
            return single
                .flatMap(this::handleErrorIfOptionsForSingle)
        }

        /**
         * Тут будут перехватываться все кривые ошибки с сервера,
         * когда HTTP code = 200, но в теле ответа содержатся ошибки
         */
        private fun handleErrorIfOptionsForObservable(response: Any): Observable<*> {
            val converter = baseModelErrorHandlerFactory.getConverter(response)
            val throwable = converter.convert(response)
            return if (throwable != null) {
                Observable.error<Any>(throwable)
            } else {
                Observable.just(response)
            }
        }

        private fun handleErrorIfOptionsForSingle(response: Any): Single<*> {
            val converter = baseModelErrorHandlerFactory.getConverter(response)
            val throwable = converter.convert(response)
            return if (throwable != null) {
                Single.error<Any>(throwable)
            } else {
                Single.just(response)
            }
        }
    }
}

